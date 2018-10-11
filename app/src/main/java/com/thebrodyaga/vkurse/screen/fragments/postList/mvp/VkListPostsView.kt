package com.thebrodyaga.vkurse.screen.fragments.postList.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.thebrodyaga.vkurse.domain.entities.ui.ItemModel
import com.thebrodyaga.vkurse.domain.entities.ui.postList.ItemsForPostList


/**
 * Created by Emelyanov.N4
 *         on 22.02.2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface VkListPostsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun choiceForegroundView(viewFlag: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideRefreshing()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorToast()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun scrollTop()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateList(searchResponse: List<ItemModel<ItemsForPostList>>)
}