package com.thebrodyaga.vkurse.mvp.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.App
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.mvp.models.gson.VkWallBody
import com.thebrodyaga.vkurse.mvp.models.gson.VkWallResponse
import com.thebrodyaga.vkurse.mvp.models.gson.testOwnerInfoList
import com.thebrodyaga.vkurse.mvp.views.VkListPostsView
import com.thebrodyaga.vkurse.net.VkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by Emelyanov.N4
 *         on 22.02.2018.
 */
@InjectViewState
class VkListPostsPresenter : BasePresenter<VkListPostsView>() {
    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var vkService: VkService
    private lateinit var currentState: VkWallBody

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        currentState = VkWallBody(timeStep = VkService.timeStep, ownerInfoList = testOwnerInfoList)
        viewState.toggleFullScreenProgress(true)
        loadFirstWall()
    }

    fun onErrorButtonClick() {
        viewState.toggleErrorButton(false)
        viewState.toggleFullScreenProgress(true)
        loadFirstWall()
    }

    fun loadNewWall() {
        val disposable: Disposable =
                vkService.getNewWall(currentState)
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
                        })
        unSubscribeOnDestroy(disposable)
    }

    fun loadAfterLast() {
        val disposable: Disposable =
                vkService.getListAfterLast(currentState)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.d(DEBUG_TAG, "loadWallAfterLast successful")
                            setCurrentState(it, last = it.wallPostList.last().date)
                            viewState.setAfterLastData(it.wallPostList)
                        }, {
                            Log.e(DEBUG_TAG, "loadWallAfterLast error: " + it.message)
                            viewState.hideProgressItem()
                            viewState.showErrorToast()
                        })
        unSubscribeOnDestroy(disposable)
    }

    private fun loadFirstWall() {
        val disposable: Disposable =
                vkService.getFirstList(currentState)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.d(DEBUG_TAG, "loadFirstWall successful")
                            setCurrentState(it, first = it.wallPostList.first().date,
                                    last = it.wallPostList.last().date)
                            viewState.setFirstData(it.wallPostList)
                            viewState.toggleFullScreenProgress(false)
                        }, {
                            Log.e(DEBUG_TAG, "loadFirstWall error: " + it.message)
                            viewState.toggleErrorButton(true)
                            viewState.toggleFullScreenProgress(false)
                        })
        unSubscribeOnDestroy(disposable)
    }

    private fun setCurrentState(it: VkWallResponse, last: Int? = null, first: Int? = null) {
        if (last != null) currentState.lastPostDate = last
        if (first != null) currentState.firstPostDate = first
        currentState.ownerInfoList = it.ownerInfoList
    }
}