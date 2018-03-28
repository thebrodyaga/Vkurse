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
    private var currentOffset = 0
    private var currentQuery = ""

    override fun onFirstViewAttach() {
        viewState.toggleSearchFragment(false)
    }

    private fun newSearchGroups(query: String) {
        Log.i(DEBUG_TAG, "newSearchGroups")
        val disposable: Disposable =
                vkService.searchGroups(query)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            currentQuery = query
                            currentOffset = it.items.size
                            Log.d(DEBUG_TAG, "newSearchGroups successful")
                            viewState.setNewResult(it)
                            viewState.toggleProgress(false)
                        }, {
                            Log.e(DEBUG_TAG, "newSearchGroups error: " + it.message)
                            viewState.toggleProgress(false)
                            viewState.showErrorToast()
                        })
        unSubscribeOnDestroy(disposable)
    }

    fun offsetSearchGroups() {
        Log.i(DEBUG_TAG, "offsetSearchGroups")
        val disposable: Disposable =
                vkService.searchGroups(currentQuery, currentOffset)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.d(DEBUG_TAG, "offsetSearchGroups successful")
                            currentOffset += it.items.size
                            viewState.setOffsetResult(it)
                        }, {
                            Log.e(DEBUG_TAG, "offsetSearchGroups error: " + it.message)
                            viewState.hideProgressItem()
                            viewState.showErrorToast()
                        })
        unSubscribeOnDestroy(disposable)
    }

    fun startSearch(query: String) {
        clearDisposable()
        viewState.toggleSearchFragment(true)
        viewState.toggleProgress(true)
        searchHandler.removeCallbacksAndMessages(null)
        searchHandler.postDelayed({ newSearchGroups(query) }, 1000)
    }

    fun stopSearch() {
        clearDisposable()
        viewState.toggleSearchFragment(false)
        searchHandler.removeCallbacksAndMessages(null)
    }
}