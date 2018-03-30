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
    init {

    }

    override fun onFirstViewAttach() {
        getFavoriteGroups()
    }

    private fun getFavoriteGroups() {
        viewState.setFavoriteGroups(listOf(Group(), Group()))
    }
}