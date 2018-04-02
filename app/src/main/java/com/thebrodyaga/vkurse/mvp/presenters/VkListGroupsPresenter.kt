package com.thebrodyaga.vkurse.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkurse.mvp.views.VkListGroupsView

/**
 * Created by Emelyanov.N4
 *         on 28.03.2018
 */
@InjectViewState
class VkListGroupsPresenter : MvpPresenter<VkListGroupsView>() {

    private var fullList = listOf(Group(), Group())
    override fun onFirstViewAttach() {
        getFullGroups()
    }

    fun getFullGroups() {
        viewState.showFullGroupsList(fullList)
    }

    fun getFilteredList(query: String) {
        viewState.showFilteredGroupsList(/*fullList.filter { it.name.startsWith(query) }*/fullList)
    }
}