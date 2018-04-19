package com.thebrodyaga.vkurse.ui.list.groups.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.thebrodyaga.vkobjects.groups.responses.SearchResponse

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */
@StateStrategyType(SingleStateStrategy::class)
interface SearchGroupsView : MvpView {

    fun showTextHeader()

    fun showProgressHeader()

    fun clearSearchList()

    @StateStrategyType(AddToEndStrategy::class)
    fun setNewSearchGroup(searchResponse: SearchResponse)

    @StateStrategyType(AddToEndStrategy::class)
    fun setOffsetSearchGroup(searchResponse: SearchResponse)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun tootleProgressItem(isVisible: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorToast()
}