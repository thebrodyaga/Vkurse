package com.thebrodyaga.vkurse.mvp.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.App
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.mvp.models.gson.VkWallBody
import com.thebrodyaga.vkurse.mvp.models.gson.testOwnerInfoList
import com.thebrodyaga.vkurse.mvp.views.VkListView
import com.thebrodyaga.vkurse.net.VkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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


    private fun getFirstWall() {
        val disposable: Disposable =
                vkService.getFirstList(VkWallBody(timeStep = VkService.timeStep, ownerInfoList = testOwnerInfoList))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            Log.d(DEBUG_TAG, "getFirstWall successful")
                            viewState.setData(it.wallPostList)
                        }, {
                            Log.d(DEBUG_TAG, "getFirstWall error: " + it.message)
                            throw RuntimeException(it)
                        })
        unsubscribeOnDestroy(disposable)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getFirstWall()
    }
}