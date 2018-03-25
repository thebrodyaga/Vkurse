package com.thebrodyaga.vkurse.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.thebrodyaga.vkurse.mvp.views.NavigationBarView

/**
 * Created by Emelyanov.N4 on 19.02.2018.
 */

@InjectViewState
class NavigationBarPresenter : MvpPresenter<NavigationBarView>() {
    fun onBottomBarClick(menuPosition: Int): Boolean {
        when (menuPosition) {
            ListPostsFragmentPosition -> {
                viewState.showListPostsFragment()
            }
            ListGroupsFragmentPosition -> {
                viewState.showListGroupsFragment()
            }
            ChatFragmentPosition -> {
                viewState.showChatFragment()
            }
        }
        return true
    }
    companion object {
        const val ListPostsFragmentPosition = 0
        const val ListGroupsFragmentPosition = 1
        const val ChatFragmentPosition = 2
    }
}