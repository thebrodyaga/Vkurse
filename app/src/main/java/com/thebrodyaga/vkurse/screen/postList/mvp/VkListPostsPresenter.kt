package com.thebrodyaga.vkurse.screen.postList.mvp

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.application.App
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.common.timeStep
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.models.gson.OwnerInfo
import com.thebrodyaga.vkurse.models.gson.VkWallBody
import com.thebrodyaga.vkurse.models.gson.VkWallResponse
import com.thebrodyaga.vkurse.models.room.VkGroup
import com.thebrodyaga.vkurse.repository.PostRepository
import com.thebrodyaga.vkurse.screen.base.BasePresenter
import com.thebrodyaga.vkurse.screen.main.mvp.MainInteractor
import com.thebrodyaga.vkurse.screen.main.mvp.MainPresenter.Companion.ListPostsFragmentPosition
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

/**
 * Created by Emelyanov.N4
 *         on 22.02.2018.
 */
@InjectViewState
class VkListPostsPresenter @Inject constructor(private val mainInteractor: MainInteractor,
                                               private val postRepository: PostRepository)
    : BasePresenter<VkListPostsView>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var isVisible = false
    private var isNeedReload = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.choiceForegroundView(PROGRESS_VIEW_FLAG)
        subscribeOnScroll()
        subscribeOnGroups()
        subscribeOnVisible()
    }

    fun onErrorButtonClick() {
        viewState.choiceForegroundView(PROGRESS_VIEW_FLAG)
        loadFirstWall()
    }

    fun loadNewWall() {
        unSubscribeOnDestroy(postRepository.loadNewWall()
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { viewState.hideRefreshing() }
                .subscribe({
                    Log.d(DEBUG_TAG, "loadNewWall successful")
                    postRepository.setCurrentState(it, first = it.wallPostList.firstOrNull()?.date)
                    viewState.setNewData(it.wallPostList)
                }, {
                    Log.e(DEBUG_TAG, "loadFirstWall error: " + it.message)
                    viewState.showErrorToast()
                }))
    }

    fun loadAfterLast() {
        viewState.tootleProgressItem(true)
        unSubscribeOnDestroy(postRepository.loadAfterLast()
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { viewState.tootleProgressItem(false) }
                .subscribe({
                    Log.d(DEBUG_TAG, "loadWallAfterLast successful")
                    viewState.setAfterLastData(it.wallPostList)
                }, {
                    Log.e(DEBUG_TAG, "loadWallAfterLast error: " + it.message)
                    viewState.showErrorToast()
                }))
    }

    private fun loadFirstWall() {
        isNeedReload = false
        unSubscribeOnDestroy(postRepository.loadFirstWall()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(DEBUG_TAG, "loadFirstWall successful")
                    postRepository.setCurrentState(it, first = it.wallPostList.first().date,
                            last = it.wallPostList.last().date)
                    viewState.setFirstData(it.wallPostList)
                    viewState.choiceForegroundView(DATA_VIEW_FLAG)
                }, {
                    Log.e(DEBUG_TAG, "loadFirstWall error: " + it.message)
                    viewState.choiceForegroundView(ERROR_VIEW_FLAG)
                }))
    }

    private fun subscribeOnScroll() {
        compositeDisposable.add(mainInteractor.scrollObservable
                .subscribe({ if (it == ListPostsFragmentPosition) viewState.scrollTop() }))
    }

    private fun subscribeOnGroups() {
        compositeDisposable.add(mainInteractor.getFavoriteGroups()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    clearDisposable()
                    if (it.isNotEmpty()) {
                        viewState.choiceForegroundView(PROGRESS_VIEW_FLAG)
                        postRepository.newCurrentState(it)
                        if (isVisible) loadFirstWall()
                        else isNeedReload = true
                    } else viewState.choiceForegroundView(EMPTY_VIEW_FLAG)
                    Log.i(DEBUG_TAG, "Favorite group update")
                }))
    }

    private fun subscribeOnVisible() {
        compositeDisposable.add(mainInteractor.visibleObservable
                .subscribe({
                    if (it == ListPostsFragmentPosition) {
                        isVisible = true
                        if (isNeedReload) loadFirstWall()
                    } else isVisible = false
                }))
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object {
        const val ERROR_VIEW_FLAG = "errorViewFlag"
        const val DATA_VIEW_FLAG = "dataViewFlag"
        const val PROGRESS_VIEW_FLAG = "progressViewFlag"
        const val EMPTY_VIEW_FLAG = "emptyViewFlag"
    }
}