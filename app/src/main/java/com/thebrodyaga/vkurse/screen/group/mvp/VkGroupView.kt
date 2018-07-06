package com.thebrodyaga.vkurse.screen.group.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by admin
 *         on 05.07.2018.
 */
interface VkGroupView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun errorFinish()
}