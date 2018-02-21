package com.example.win10.vkurse.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.win10.vkurse.R
import com.example.win10.vkurse.mvp.views.NavigationBarView

/**
 * Created by Emelyanov.N4 on 19.02.2018.
 */

@InjectViewState
class NavigationBarPresenter : MvpPresenter<NavigationBarView>() {
    fun onBottomBarClick(menuPosition: Int): Boolean {
        when (menuPosition) {
            VkFragmentPosition -> {
                viewState.showVkFragment()
            }
            InstagramFragmentPosition -> {
                viewState.showInstagramFragment()
            }
            ChatFragmentPosition -> {
                viewState.showChatFragment()
            }
        }
        return true
    }
    companion object {
        const val VkFragmentPosition = 0
        const val InstagramFragmentPosition = 1
        const val ChatFragmentPosition = 2
    }
}