package com.thebrodyaga.vkurse.ui.groupList.mvp

import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkurse.ui.base.BasePresenter
import com.thebrodyaga.vkurse.ui.main.mvp.MainInteractor
import com.thebrodyaga.vkurse.ui.main.mvp.MainPresenter.Companion.ListGroupsFragmentPosition

/**
 * Created by Emelyanov.N4
 *         on 28.03.2018
 */
@InjectViewState
class VkListGroupsPresenter(private val mainInteractor: MainInteractor) : BasePresenter<VkListGroupsView>() {

    private var fullList = listOf(Group(), Group())

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