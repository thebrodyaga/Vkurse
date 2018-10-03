package com.thebrodyaga.vkurse.screen.fragments.postList.mvp

import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.application.di.Injector
import com.thebrodyaga.vkurse.common.debugLogging
import com.thebrodyaga.vkurse.repository.PostRepository
import com.thebrodyaga.vkurse.screen.base.BasePresenter
import com.thebrodyaga.vkurse.screen.fragments.main.mvp.MainInteractor
import com.thebrodyaga.vkurse.screen.fragments.main.mvp.MainPresenter.Companion.ListPostsFragmentPosition
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by Emelyanov.N4
 *         on 22.02.2018.
 */
@InjectViewState
class VkListPostsPresenter @Inject constructor(private val postRepository: PostRepository)
    : BasePresenter<VkListPostsView>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mainInteractor: MainInteractor = Injector.plusMainComponent().getMainInteractor()
    private var loadAfterLastDisposable: Disposable? = null

    private var isVisible = false
    private var isNeedReload = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.choiceForegroundView(PROGRESS_VIEW_FLAG)
        viewState.tootleProgressItem(false)
        subscribeOnScroll()
//        subscribeOnGroups()
        loadFirstWall()
        subscribeOnVisible()
    }

    fun onErrorButtonClick() {
        viewState.choiceForegroundView(PROGRESS_VIEW_FLAG)
        loadFirstWall()
    }

    fun loadNewWall() {
        compositeDisposable.add(postRepository.loadNewWall()
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { viewState.hideRefreshing() }
                .subscribe({
                    debugLogging("loadNewWall successful")
                    viewState.setNewData(it)
                }, {
                    debugLogging("loadFirstWall error: " + it.message)
                    viewState.showErrorToast()
                }))
    }

    fun loadAfterLast() {
        if (loadAfterLastDisposable?.isDisposed == false) return
        loadAfterLastDisposable = (postRepository.loadAfterLast()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.tootleProgressItem(true) }
                .doFinally { viewState.tootleProgressItem(false) }
                .subscribe({
                    debugLogging("loadWallAfterLast successful")
                    viewState.setAfterLastData(it)
                }, {
                    debugLogging("loadWallAfterLast error: " + it.message)
                    viewState.showErrorToast()
                }))
    }

    private fun loadFirstWall() {
        isNeedReload = false
        compositeDisposable.add(postRepository.loadFirstWall()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    debugLogging("loadFirstWall successful")
                    viewState.setFirstData(it)
                    viewState.choiceForegroundView(DATA_VIEW_FLAG)
                }, {
                    debugLogging("loadFirstWall error: " + it.message)
                    it.printStackTrace()
                    viewState.choiceForegroundView(ERROR_VIEW_FLAG)
                }))
    }

    private fun subscribeOnScroll() {
        unSubscribeOnDestroy(mainInteractor.scrollObservable
                .subscribe { if (it == ListPostsFragmentPosition) viewState.scrollTop() })
    }

    private fun subscribeOnGroups() {
        unSubscribeOnDestroy(mainInteractor.getFavoriteGroups()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    clearDisposable()
                    if (it.isNotEmpty()) {
                        viewState.choiceForegroundView(PROGRESS_VIEW_FLAG)
                        postRepository.newCurrentState(it)
                        if (isVisible)
                            loadFirstWall()
                        else isNeedReload = true
                    } else viewState.choiceForegroundView(EMPTY_VIEW_FLAG)
                    debugLogging("Favorite group update")
                })
    }

    private fun clearDisposable() {
        loadAfterLastDisposable?.dispose()
        compositeDisposable.clear()
    }

    private fun subscribeOnVisible() {
        unSubscribeOnDestroy(mainInteractor.visibleObservable
                .subscribe {
                    if (it == ListPostsFragmentPosition) {
                        isVisible = true
                        if (isNeedReload) loadFirstWall()
                    } else isVisible = false
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        clearDisposable()
    }

    companion object {
        const val ERROR_VIEW_FLAG = "errorViewFlag"
        const val DATA_VIEW_FLAG = "dataViewFlag"
        const val PROGRESS_VIEW_FLAG = "progressViewFlag"
        const val EMPTY_VIEW_FLAG = "emptyViewFlag"
    }
}