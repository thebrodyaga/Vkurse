package com.thebrodyaga.vkurse.mvp.presenters

import android.os.Handler
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.App
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.mvp.views.VkListSearchGroupsView
import com.thebrodyaga.vkurse.net.VkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */
@InjectViewState
class VkListSearchGroupsPresenter : BasePresenter<VkListSearchGroupsView>() {
    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var vkService: VkService
    private val searchHandler = Handler()

    private fun newSearchGroups(query: String) {
        Log.i(DEBUG_TAG, "newSearchGroups")
        val disposable: Disposable =
                vkService.searchGroups(query)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            viewState.setNewResult(it)
                            viewState.toggleProgress(false)
                        }, {
                            viewState.toggleProgress(false)
                            viewState.showErrorToast()
                        })
        unSubscribeOnDestroy(disposable)
    }

    fun offsetSearchGroups(query: String, offset: Int) {
        Log.i(DEBUG_TAG, "offsetSearchGroups")
        val disposable: Disposable =
                vkService.searchGroups(query, offset)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({}, {})
        unSubscribeOnDestroy(disposable)
    }

    fun startHandler(query: String) {
        Log.i(DEBUG_TAG, "startHandler")
        clearDisposable()
        viewState.toggleProgress(true)
        searchHandler.removeCallbacksAndMessages(null)
        searchHandler.postDelayed({ newSearchGroups(query) }, 1000)
    }
}