package com.thebrodyaga.vkurse.screen.fragments.groupList.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.thebrodyaga.vkurse.domain.entities.ui.ItemModel
import com.thebrodyaga.vkurse.domain.entities.ui.groupsList.ItemsForGroupsList

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */
interface VkListGroupsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateList(searchResponse: List<ItemModel<ItemsForGroupsList>>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorToast()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun scrollTop()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setSearchText(query: String?)
}