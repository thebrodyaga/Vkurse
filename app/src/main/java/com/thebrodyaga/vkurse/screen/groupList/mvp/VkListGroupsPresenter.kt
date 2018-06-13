package com.thebrodyaga.vkurse.screen.groupList.mvp

import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkurse.screen.base.BasePresenter
import com.thebrodyaga.vkurse.screen.main.mvp.MainInteractor
import com.thebrodyaga.vkurse.screen.main.mvp.MainPresenter.Companion.ListGroupsFragmentPosition
import javax.inject.Inject

/**
 * Created by Emelyanov.N4
 *         on 28.03.2018
 */
@InjectViewState
class VkListGroupsPresenter @Inject constructor(private val mainInteractor: MainInteractor)
    : BasePresenter<VkListGroupsView>() {

    private var fullList = listOf(Group(), Group(),Group(), Group(),Group(), Group(),Group(), Group(),Group(), Group(),Group(), Group(),Group(), Group(),Group(), Group(),Group(), Group(),Group(), Group())

    override fun onFirstViewAttach() {
        getFullGroups()
        subscribeOnScroll()
        subscribeOnSearch()
    }

    private fun getFullGroups() {
        viewState.showFullGroupsList(fullList)
    }

    private fun getFilteredList(query: String) {
        viewState.showFilteredGroupsList(/*fullList.filter { it.name.startsWith(query) }*/fullList)
    }

    private fun subscribeOnSearch() {
        unSubscribeOnDestroy(mainInteractor.searchObservable
                .subscribe({
                    if (it.isNotBlank()) getFilteredList(it)
                    else getFullGroups()
                }))
    }

    private fun subscribeOnScroll() {
        unSubscribeOnDestroy(mainInteractor.scrollObservable
                .subscribe({ if (it == ListGroupsFragmentPosition) viewState.scrollTop() }))
    }
}