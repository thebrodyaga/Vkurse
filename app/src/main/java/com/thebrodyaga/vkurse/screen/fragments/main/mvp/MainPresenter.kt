package com.thebrodyaga.vkurse.screen.fragments.main.mvp

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.application.di.Injector
import com.thebrodyaga.vkurse.common.debugLogging
import com.thebrodyaga.vkurse.screen.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by Emelyanov.N4
 *         on 19.02.2018.
 */
@InjectViewState
class MainPresenter @Inject constructor(private val router: Router) : BasePresenter<MainView>() {
    private var mainInteractor: MainInteractor = Injector.plusMainComponent().getMainInteractor()

    override fun onFirstViewAttach() {
        debugLogging( "onFirstViewAttach MainPresenter")
    }

    fun onBottomBarReClick(menuPosition: Int): Boolean {
        mainInteractor.scrollToTop(menuPosition)
        return true
    }

    fun onBottomBarClick(menuPosition: Int): Boolean {
        when (menuPosition) {
            MainView.POSTS_PAGE.first -> {
                viewState.showPage(MainView.POSTS_PAGE.second)
            }
            MainView.SEARCH_PAGE.first -> {
                viewState.showPage(MainView.SEARCH_PAGE.second)
            }
            MainView.CHAT_PAGE.first -> {
                viewState.showPage(MainView.CHAT_PAGE.second)
            }
        }
        return true
    }

    override fun onBackPressed() {
        router.exit()
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