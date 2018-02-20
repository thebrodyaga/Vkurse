package com.example.win10.vkurse.mvp.presenters

import android.view.MenuItem
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.win10.vkurse.mvp.views.ScrollToTopView

/**
 * Created by Emelyanov.N4 on 19.02.2018.
 */



@InjectViewState
class ScrollToTopPresenter : MvpPresenter<ScrollToTopView>() {
    companion object {
        const val ScrollToTopPresenterTAG: String = "ScrollToTopPresenterTag"
    }
    fun onBottomBarReClick(menuItem: MenuItem): Boolean {
        viewState.scrollTop(menuItem)
        return true
    }
}