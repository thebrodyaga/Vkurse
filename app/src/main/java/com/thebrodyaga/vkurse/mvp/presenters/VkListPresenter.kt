package com.thebrodyaga.vkurse.mvp.presenters

import com.thebrodyaga.vkurse.App
import com.thebrodyaga.vkurse.mvp.views.VkListView
import com.thebrodyaga.vkurse.net.VkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Emelyanov.N4 on 22.02.2018.
 */
class VkListPresenter : BasePresenter<VkListView>() {
    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var vkService: VkService


    fun getFirstWall() {
        /*val disposable: Disposable =
                vkService.getFirstList(null)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            viewState.setData(it.wallPostList)
                        }, {
                            throw RuntimeException(it)
                        })
        unsubscribeOnDestroy(disposable)*/
    }
}