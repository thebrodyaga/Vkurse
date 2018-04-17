package com.thebrodyaga.vkurse.ui.list.posts.mvp

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.application.App
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.models.gson.VkWallBody
import com.thebrodyaga.vkurse.models.gson.VkWallResponse
import com.thebrodyaga.vkurse.models.gson.testOwnerInfoList
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Created by Emelyanov.N4
 *         on 22.02.2018.
 */
@InjectViewState
class VkListPostsPresenter : BasePresenter<VkListPostsView>() {

//    val vkService = App.appComponent.getVkService()
    private lateinit var currentState: VkWallBody

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        currentState = VkWallBody(timeStep = VkService.timeStep, ownerInfoList = testOwnerInfoList)
        viewState.choiceForegroundView(PROGRESS_VIEW_FLAG)
        loadFirstWall()
    }

    fun onErrorButtonClick() {
        viewState.choiceForegroundView(PROGRESS_VIEW_FLAG)
        loadFirstWall()
    }

    fun loadNewWall() {
        /*unSubscribeOnDestroy(vkService.getNewWall(currentState)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(DEBUG_TAG, "loadNewWall successful")
                    setCurrentState(it, first = it.wallPostList.firstOrNull()?.date)
                    viewState.setNewData(it.wallPostList)
                    viewState.hideRefreshing()
                }, {
                    Log.e(DEBUG_TAG, "loadFirstWall error: " + it.message)
                    viewState.showErrorToast()
                    viewState.hideRefreshing()
                }))*/
    }

    fun loadAfterLast() {
        /*viewState.tootleProgressItem(true)
        val disposable: Disposable =
                vkService.getListAfterLast(currentState)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.d(DEBUG_TAG, "loadWallAfterLast successful")
                            viewState.tootleProgressItem(false)
                            viewState.setAfterLastData(it.wallPostList)
                        }, {
                            Log.e(DEBUG_TAG, "loadWallAfterLast error: " + it.message)
                            viewState.tootleProgressItem(false)
                            viewState.showErrorToast()
                        })
        unSubscribeOnDestroy(disposable)*/
    }

    private fun loadFirstWall() {
        /*val disposable: Disposable =
                vkService.getFirstList(currentState)
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
                        })
        unSubscribeOnDestroy(disposable)*/
    }

    private fun setCurrentState(it: VkWallResponse, last: Int? = null, first: Int? = null) =
            synchronized(this) {
                if (last != null) currentState.lastPostDate = last
                if (first != null) currentState.firstPostDate = first
                currentState.ownerInfoList = it.ownerInfoList
            }


    companion object {
        const val ERROR_VIEW_FLAG = "errorViewFlag"
        const val DATA_VIEW_FLAG = "dataViewFlag"
        const val PROGRESS_VIEW_FLAG = "progressViewFlag"
        const val EMPTY_VIEW_FLAG = "emptyViewFlag"
    }
}