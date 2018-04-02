package com.thebrodyaga.vkurse.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thebrodyaga.vkobjects.groups.Group

/**
 * Created by Emelyanov.N4
 *         on 28.03.2018
 */

interface VkListGroupsView : MvpView {
    @StateStrategyType(SingleStateStrategy::class)
    fun showFilteredList(filteredList: List<Group>)

    @StateStrategyType(SingleStateStrategy::class)
    fun showFullList(fullList: List<Group>)
}