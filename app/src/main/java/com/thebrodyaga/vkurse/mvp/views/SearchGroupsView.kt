package com.thebrodyaga.vkurse.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkobjects.groups.responses.SearchResponse

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */
interface SearchGroupsView : MvpView {

    @StateStrategyType(SingleStateStrategy::class)
    fun setNewSearchGroup(searchResponse: SearchResponse)

    @StateStrategyType(AddToEndStrategy::class)
    fun setOffsetSearchGroup(searchResponse: SearchResponse)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun tootleProgressItem(isVisible: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorToast()

    @StateStrategyType(SingleStateStrategy::class)
    fun stopSearch()
}