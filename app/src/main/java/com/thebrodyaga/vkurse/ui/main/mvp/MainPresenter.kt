package com.thebrodyaga.vkurse.ui.main.mvp

import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.ui.base.BasePresenter

/**
 * Created by Emelyanov.N4
 *         on 19.02.2018.
 */
@InjectViewState
class MainPresenter(private val mainActivityModel: MainActivityModel)
    : BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        viewState.showListPostsFragment()
        unSubscribeOnDestroy(mainActivityModel
                .scrollObservable
                .subscribe({ viewState.scrollTop() }))
    }

    fun onToolbarItemSelected(item: Int?) {
        when (item) {
            /*R.id.toolbar_settings -> {
                viewState.startSettingActivity()
            }*/
        }
    }

    fun toggleSearchIcon(isVisible: Boolean) {
        viewState.toggleSearchIcon(isVisible)
    }

    fun searchControl(query: String?) {
        mainActivityModel.searchControl(query)
    }

    fun onBottomBarReClick(menuPosition: Int): Boolean {
        mainActivityModel.scrollToTop(menuPosition)
        return true
    }

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