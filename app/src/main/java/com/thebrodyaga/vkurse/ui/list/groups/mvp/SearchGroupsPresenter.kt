package com.thebrodyaga.vkurse.ui.list.groups.mvp

import android.os.Handler
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.application.App
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.ui.base.BasePresenter
import com.thebrodyaga.vkurse.ui.main.mvp.models.MainActivityModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */
@InjectViewState
class SearchGroupsPresenter(private val mainActivityModel: MainActivityModel) : BasePresenter<SearchGroupsView>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val vkService = App.appComponent.getVkService()
    private val searchHandler = Handler()
    private var currentOffset = 0
    private var currentQuery = ""

    override fun onFirstViewAttach() {
        subscribeOnSearch()
    }

    private fun subscribeOnSearch() {
        compositeDisposable.add(mainActivityModel.searchObservable
                .subscribe({
                    if (it.isNotBlank()) startSearch(it)
                    else stopSearch()
                }))
    }

    private fun newSearchGroups(query: String) {
        unSubscribeOnDestroy(vkService.searchGroups(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(DEBUG_TAG, "newSearchGroups successful")
                    currentQuery = query
                    currentOffset = it.items.size
                    viewState.setNewSearchGroup(it)
                }, {
                    Log.e(DEBUG_TAG, "newSearchGroups error: " + it.message)
                    viewState.showErrorToast()
                    viewState.setNewSearchGroup(null)
                }))
    }

    fun offsetSearchGroups() {
        viewState.tootleProgressItem(true)
        unSubscribeOnDestroy(vkService.searchGroups(currentQuery, currentOffset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(DEBUG_TAG, "offsetSearchGroups successful")
                    currentOffset += it.items.size
                    viewState.tootleProgressItem(false)
                    viewState.setOffsetSearchGroup(it)
                }, {
                    Log.e(DEBUG_TAG, "offsetSearchGroups error: " + it.message)
                    viewState.showErrorToast()
                    viewState.tootleProgressItem(false)
                }))
    }

    private fun startSearch(query: String) {
        clearDisposable()
        searchHandler.removeCallbacksAndMessages(null)
        searchHandler.postDelayed({ newSearchGroups(query) }, 1000)
    }

    private fun stopSearch() {
        currentOffset = 0
        currentQuery = ""
        clearDisposable()
        searchHandler.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}