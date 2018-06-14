package com.thebrodyaga.vkurse.screen.main.mvp

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.application.di.Injector
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.screen.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Emelyanov.N4
 *         on 19.02.2018.
 */
@InjectViewState
class MainPresenter @Inject constructor() : BasePresenter<MainView>() {
    private var mainInteractor: MainInteractor = Injector.plusMainComponent().getMainInteractor()

    override fun onFirstViewAttach() {
        Log.d(DEBUG_TAG, "onFirstViewAttach MainPresenter")
        unSubscribeOnDestroy(mainInteractor
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

    fun searchControl(query: String?) {
        mainInteractor.searchControl(query)
    }

    fun onBottomBarReClick(menuPosition: Int): Boolean {
        mainInteractor.scrollToTop(menuPosition)
        return true
    }

    fun onBottomBarClick(menuPosition: Int): Boolean {
        mainInteractor.visibleFragment(menuPosition)
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

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearMainComponent()
    }

    companion object {
        const val ListPostsFragmentPosition = 0
        const val ListGroupsFragmentPosition = 1
        const val ChatFragmentPosition = 2
    }
}