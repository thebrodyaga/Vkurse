package com.thebrodyaga.vkurse.mvp.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.App
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.mvp.models.gson.VkWallBody
import com.thebrodyaga.vkurse.mvp.models.gson.VkWallResponse
import com.thebrodyaga.vkurse.mvp.models.gson.testOwnerInfoList
import com.thebrodyaga.vkurse.mvp.views.VkListView
import com.thebrodyaga.vkurse.net.VkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by Emelyanov.N4 on 22.02.2018.
 */
@InjectViewState
class VkListPresenter : BasePresenter<VkListView>() {
    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var vkService: VkService
    lateinit var currentState: VkWallBody

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        currentState = VkWallBody(timeStep = VkService.timeStep, ownerInfoList = testOwnerInfoList)
        loadFirstWall()
    }

    fun onErrorButtonClick() {
        viewState.showListProgress()
        loadFirstWall()
    }

    fun onSwipeRefresh() {

    }

    fun loadMore() {
        loadWallAfterLast()
    }

    private fun loadWallAfterLast() {
        val disposable: Disposable =
                vkService.getListAfterLast(currentState)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.d(DEBUG_TAG, "loadWallAfterLast successful")
                            setCurrentState(it)
                            viewState.setData(it.wallPostList)
                        }, {
                            Log.e(DEBUG_TAG, "loadWallAfterLast error: " + it.message)
                            viewState.hideProgressItem()
                        })
        unsubscribeOnDestroy(disposable)
    }

    private fun loadFirstWall() {
        val disposable: Disposable =
                vkService.getFirstList(currentState)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.d(DEBUG_TAG, "loadFirstWall successful")
                            setCurrentState(it)
                            viewState.setData(it.wallPostList)
                            viewState.hideListProgress()
                        }, {
                            Log.e(DEBUG_TAG, "loadFirstWall error: " + it.message)
                            viewState.showError()
                        })
        unsubscribeOnDestroy(disposable)
    }

    private fun setCurrentState(it: VkWallResponse) {
        if (!it.wallPostList.isEmpty()) currentState.lastPostDate = it.wallPostList.last().date
        currentState.ownerInfoList = it.ownerInfoList
    }
}