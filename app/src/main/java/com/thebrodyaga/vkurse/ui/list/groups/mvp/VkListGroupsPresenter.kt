package com.thebrodyaga.vkurse.ui.list.groups.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkurse.ui.main.mvp.models.MainActivityModel

/**
 * Created by Emelyanov.N4
 *         on 28.03.2018
 */
@InjectViewState
class VkListGroupsPresenter(private val mainActivityModel: MainActivityModel) : MvpPresenter<VkListGroupsView>() {

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