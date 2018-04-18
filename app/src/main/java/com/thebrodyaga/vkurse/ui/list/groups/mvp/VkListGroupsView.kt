package com.thebrodyaga.vkurse.ui.list.groups.mvp

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
    fun showFilteredGroupsList(filteredList: List<Group>)

    @StateStrategyType(SingleStateStrategy::class)
    fun showFullGroupsList(fullList: List<Group>)
}