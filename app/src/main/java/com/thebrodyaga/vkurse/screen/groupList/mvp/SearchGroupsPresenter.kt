package com.thebrodyaga.vkurse.screen.groupList.mvp

import android.os.Handler
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.application.di.Injector
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.repository.GroupRepository
import com.thebrodyaga.vkurse.screen.base.BasePresenter
import com.thebrodyaga.vkurse.screen.fragments.main.mvp.MainInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */
@InjectViewState
class SearchGroupsPresenter @Inject constructor(private val groupRepository: GroupRepository)
    : BasePresenter<SearchGroupsView>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var mainInteractor: MainInteractor = Injector.plusMainComponent().getMainInteractor()
    private val searchHandler = Handler()

    override fun onFirstViewAttach() {
        subscribeOnSearch()
    }

    private fun subscribeOnSearch() {
        compositeDisposable.add(mainInteractor.searchObservable
                .subscribe({
                    if (it.isNotBlank()) startSearch(it)
                    else stopSearch()
                }))
    }

    private fun newSearchGroups(query: String) {
        unSubscribeOnDestroy(groupRepository.newSearchGroups(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(DEBUG_TAG, "newSearchGroups successful")
                    groupRepository.setCurrentState(query, it.items.size)
                    viewState.showTextHeader()
                    viewState.setNewSearchGroup(it)
                }, {
                    Log.e(DEBUG_TAG, "newSearchGroups error: " + it.message)
                    viewState.showTextHeader()
                    viewState.showErrorToast()
                }))
    }

    fun offsetSearchGroups() {
        viewState.tootleProgressItem(true)
        unSubscribeOnDestroy(groupRepository.offsetSearchGroups()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(DEBUG_TAG, "offsetSearchGroups successful")
                    groupRepository.setCurrentState(offset = it.items.size)
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
        viewState.clearSearchList()
        viewState.showProgressHeader()
        searchHandler.postDelayed({ newSearchGroups(query) }, 1000)
    }

    private fun stopSearch() {
        groupRepository.setCurrentState("", null)
        clearDisposable()
        viewState.clearSearchList()
        searchHandler.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}