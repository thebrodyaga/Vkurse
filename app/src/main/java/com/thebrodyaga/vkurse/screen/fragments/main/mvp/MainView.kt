package com.thebrodyaga.vkurse.screen.fragments.main.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by Emelyanov.N4
 *         on 19.02.2018.
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface MainView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showPage(fragmentTag: String)

    companion object {
        val POSTS_PAGE = Pair(0, "POSTS_PAGE_TAG")
        val SEARCH_PAGE = Pair(1, "SEARCH_PAGE_TAG")
        val CHAT_PAGE = Pair(2, "CHAT_PAGE_TAG")
    }

    /*fun startSettingActivity()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun toggleSearchIcon(isVisible: Boolean)

    fun showListPostsFragment()

    fun showListGroupsFragment()

    fun showChatFragment()

    fun scrollTop()*/
}