package com.thebrodyaga.vkurse.screen.fragments.postList.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.thebrodyaga.vkurse.domain.entities.VkPost


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

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun tootleProgressItem(isVisible: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorToast()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun scrollTop()

    @StateStrategyType(SingleStateStrategy::class)
    fun setFirstData(wallPostList: List<VkPost>)

    @StateStrategyType(AddToEndStrategy::class)
    fun setNewData(wallPostList: List<VkPost>)

    @StateStrategyType(AddToEndStrategy::class)
    fun setAfterLastData(wallPostList: List<VkPost>)
}