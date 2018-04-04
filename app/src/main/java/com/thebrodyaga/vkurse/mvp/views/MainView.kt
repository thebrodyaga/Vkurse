package com.thebrodyaga.vkurse.mvp.views

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
    fun showDefaultFragment(fragmentTag: String)

    fun startSettingActivity()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun toggleSearchIcon(isVisible: Boolean)
}