package com.example.win10.vkurse.mvp.views

import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.win10.vkurse.ui.fragments.VkListFragment

/**
 * Created by Emelyanov.N4 on 19.02.2018.
 */
@StateStrategyType(value = OneExecutionStateStrategy::class)
interface MainView : MvpView {
    fun showDefaultFragment(fragment: MvpAppCompatFragment)
}