package com.example.win10.vkurse.mvp.presenters

import android.view.MenuItem
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.win10.vkurse.R
import com.example.win10.vkurse.mvp.views.NavigationBarView

/**
 * Created by Emelyanov.N4 on 19.02.2018.
 */

@InjectViewState
class NavigationBarPresenter : MvpPresenter<NavigationBarView>() {
    fun onBottomBarClick(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.bottom_vk -> {
                viewState.showVkFragment()
            }
            R.id.bottom_instagram -> {
                viewState.showInstagramFragment()
            }
            R.id.bottom_chat -> {
                viewState.showChatFragment()
            }
        }
        return true
    }
}