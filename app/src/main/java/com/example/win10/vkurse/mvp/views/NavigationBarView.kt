package com.example.win10.vkurse.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by Emelyanov.N4 on 19.02.2018.
 */
@StateStrategyType(value = OneExecutionStateStrategy::class)
interface NavigationBarView : MvpView {
    fun showVkFragment()

    fun showInstagramFragment()

    fun showChatFragment()
}