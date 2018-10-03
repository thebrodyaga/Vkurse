package com.thebrodyaga.vkurse.screen.fragments.groupList.mvp

import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.application.di.Injector
import com.thebrodyaga.vkurse.common.debugLogging
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

    private fun newSearchGroups(query: String) {
        compositeDisposable.add(groupRepository.newSearchGroups(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    debugLogging( "newSearchGroups successful")
                    groupRepository.setCurrentState(query, it.items.size)
                    viewState.showTextHeader()
                    viewState.setNewSearchGroup(it)
                }, {
                    debugLogging( "newSearchGroups error: " + it.message)
                    viewState.showTextHeader()
                    viewState.showErrorToast()
                }))
    }

    fun offsetSearchGroups() {
        viewState.tootleProgressItem(true)
        compositeDisposable.add(groupRepository.offsetSearchGroups()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    debugLogging( "offsetSearchGroups successful")
                    groupRepository.setCurrentState(offset = it.items.size)
                    viewState.tootleProgressItem(false)
                    viewState.setOffsetSearchGroup(it)
                }, {
                    debugLogging( "offsetSearchGroups error: " + it.message)
                    viewState.showErrorToast()
                    viewState.tootleProgressItem(false)
                }))
    }

    fun startSearch(query: String) {
        compositeDisposable.clear()
        viewState.clearSearchList()
        viewState.showProgressHeader()
        newSearchGroups(query)
    }

    fun stopSearch() {
        groupRepository.setCurrentState("", null)
        compositeDisposable.clear()
        viewState.clearSearchList()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}