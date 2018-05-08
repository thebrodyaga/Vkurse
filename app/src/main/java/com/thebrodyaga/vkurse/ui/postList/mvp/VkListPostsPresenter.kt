package com.thebrodyaga.vkurse.ui.postList.mvp

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.application.App
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.models.gson.OwnerInfo
import com.thebrodyaga.vkurse.models.gson.VkWallBody
import com.thebrodyaga.vkurse.models.gson.VkWallResponse
import com.thebrodyaga.vkurse.models.room.VkGroup
import com.thebrodyaga.vkurse.ui.base.BasePresenter
import com.thebrodyaga.vkurse.ui.main.mvp.MainInteractor
import com.thebrodyaga.vkurse.ui.main.mvp.MainPresenter.Companion.ListPostsFragmentPosition
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*

/**
 * Created by Emelyanov.N4
 *         on 22.02.2018.
 */
@InjectViewState
class VkListPostsPresenter(private val mainInteractor: MainInteractor)
    : BasePresenter<VkListPostsView>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val vkService = App.appComponent.getVkService()
    private lateinit var currentState: VkWallBody
    private var isVisible = false
    private var isNeedReload = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.choiceForegroundView(PROGRESS_VIEW_FLAG)
        subscribeOnScroll()
        subscribeOnGroups()
        /*currentState = VkWallBody(timeStep = VkService.timeStep, ownerInfoList = testOwnerInfoList)
        viewState.choiceForegroundView(PROGRESS_VIEW_FLAG)
        loadFirstWall()*/
    }

    fun onErrorButtonClick() {
        viewState.choiceForegroundView(PROGRESS_VIEW_FLAG)
        loadFirstWall()
    }

    fun loadNewWall() {
        unSubscribeOnDestroy(vkService.getNewWall(currentState)
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { viewState.hideRefreshing() }
                .subscribe({
                    Log.d(DEBUG_TAG, "loadNewWall successful")
                    setCurrentState(it, first = it.wallPostList.firstOrNull()?.date)
                    viewState.setNewData(it.wallPostList)
                }, {
                    Log.e(DEBUG_TAG, "loadFirstWall error: " + it.message)
                    viewState.showErrorToast()
                }))
    }

    fun loadAfterLast() {
        viewState.tootleProgressItem(true)
        unSubscribeOnDestroy(vkService.getListAfterLast(currentState)
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
        unSubscribeOnDestroy(vkService.getFirstList(currentState)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(DEBUG_TAG, "loadFirstWall successful")
                    setCurrentState(it, first = it.wallPostList.first().date,
                            last = it.wallPostList.last().date)
                    viewState.setFirstData(it.wallPostList)
                    viewState.choiceForegroundView(DATA_VIEW_FLAG)
                }, {
                    Log.e(DEBUG_TAG, "loadFirstWall error: " + it.message)
                    viewState.choiceForegroundView(ERROR_VIEW_FLAG)
                }))
    }

    private fun newCurrentState(groupsList: List<VkGroup>) {
        val ownerInfoList = ArrayList<OwnerInfo>()
        groupsList.forEach { ownerInfoList.add(OwnerInfo(-1 * it.id!!)) }
        currentState = VkWallBody(timeStep = VkService.timeStep, ownerInfoList = ownerInfoList)
    }

    private fun setCurrentState(it: VkWallResponse, last: Int? = null, first: Int? = null) {
        if (last != null) currentState.lastPostDate = last
        if (first != null) currentState.firstPostDate = first
        currentState.ownerInfoList = it.ownerInfoList
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
                        newCurrentState(it)
                        if (isVisible) loadFirstWall()
                        else isNeedReload = true
                    } else viewState.choiceForegroundView(EMPTY_VIEW_FLAG)
                    Log.i(DEBUG_TAG, "Favorite group update")
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

    fun onStart() {
        isVisible = true
        if (isNeedReload) loadFirstWall()

    }

    fun onStop() {
        isVisible = false
    }
}