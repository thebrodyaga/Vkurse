package com.thebrodyaga.vkurse.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by Win10
 *         on 25.03.2018.
 */

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface ToolbarSearchView : MvpView {
    fun needSearch(query: String)
    fun notNeedSearch()
}