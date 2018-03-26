package com.thebrodyaga.vkurse.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.thebrodyaga.vkurse.mvp.views.ScrollToTopView

/**
 * Created by Emelyanov.N4
 *         on 19.02.2018.
 */



@InjectViewState
class ScrollToTopPresenter : MvpPresenter<ScrollToTopView>() {
    companion object {
        const val ScrollToTopPresenterTAG: String = "ScrollToTopPresenterTag"
    }
    fun onBottomBarReClick(menuPosition: Int): Boolean {
        viewState.scrollTop(menuPosition)
        return true
    }
}