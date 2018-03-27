package com.thebrodyaga.vkurse.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.thebrodyaga.vkobjects.groups.responses.SearchResponse

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */
interface VkListSearchGroupsView : MvpView {

    @StateStrategyType(SingleStateStrategy::class)
    fun setNewResult(searchResponse: SearchResponse)

    @StateStrategyType(AddToEndStrategy::class)
    fun setOffsetResult(searchResponse: SearchResponse)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun toggleProgress(isVisible: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorToast()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideProgressItem()
}