package com.thebrodyaga.vkurse.mvp.presenters

import android.os.Handler
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.App
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.mvp.views.SearchGroupsView
import com.thebrodyaga.vkurse.net.VkService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */
@InjectViewState
class SearchGroupsPresenter : BasePresenter<SearchGroupsView>() {
    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var vkService: VkService
    private val searchHandler = Handler()
    private var currentOffset = 0
    private var currentQuery = ""

    private fun newSearchGroups(query: String) {
        val disposable: Disposable =
                vkService.searchGroups(query)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.d(DEBUG_TAG, "newSearchGroups successful")
                            currentQuery = query
                            currentOffset = it.items.size
                            viewState.setNewResult(it)
                        }, {
                            Log.e(DEBUG_TAG, "newSearchGroups error: " + it.message)
                            viewState.showErrorToast()
                        })
        unSubscribeOnDestroy(disposable)
    }

    fun offsetSearchGroups() {
        viewState.tootleProgressItem(true)
        val disposable: Disposable =
                vkService.searchGroups(currentQuery, currentOffset)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.d(DEBUG_TAG, "offsetSearchGroups successful")
                            currentOffset += it.items.size
                            viewState.tootleProgressItem(false)
                            viewState.setOffsetResult(it)
                        }, {
                            Log.e(DEBUG_TAG, "offsetSearchGroups error: " + it.message)
                            viewState.showErrorToast()
                            viewState.tootleProgressItem(false)
                        })
        unSubscribeOnDestroy(disposable)
    }

    fun startSearch(query: String) {
        clearDisposable()
        searchHandler.removeCallbacksAndMessages(null)
        searchHandler.postDelayed({ newSearchGroups(query) }, 1000)
    }

    fun stopSearch() {
        clearDisposable()
        viewState.stopSearch()
        searchHandler.removeCallbacksAndMessages(null)
    }
}