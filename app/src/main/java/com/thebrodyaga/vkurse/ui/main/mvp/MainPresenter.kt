package com.thebrodyaga.vkurse.ui.main.mvp

import android.support.v7.widget.SearchView
import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.ui.base.BasePresenter

/**
 * Created by Emelyanov.N4
 *         on 19.02.2018.
 */
@InjectViewState
class MainPresenter(private val mainActivityModel: MainActivityModel)
    : BasePresenter<MainView>(), SearchView.OnQueryTextListener {

    override fun onFirstViewAttach() {
        viewState.showListPostsFragment()
        unSubscribeOnDestroy(mainActivityModel
                .scrollObservable
                .subscribe({ viewState.scrollTop() }))
    }

    fun onToolbarItemSelected(item: Int?) {
        when (item) {
            R.id.toolbar_settings -> {
                viewState.startSettingActivity()
            }
        }
    }

    fun toggleSearchIcon(isVisible: Boolean) {
        viewState.toggleSearchIcon(isVisible)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        mainActivityModel.searchControl(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        mainActivityModel.searchControl(newText)
        return false
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