package com.thebrodyaga.vkurse.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.thebrodyaga.vkobjects.wall.WallPostFull


/**
 * Created by Emelyanov.N4 on 22.02.2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface VkListView : MvpView {

    fun showErrorButton()

    fun toggleSwipeRefresh(isEnable: Boolean)

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun hideRefreshing()

    fun toggleFullScreenProgress(isVisible: Boolean)

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun hideProgressItem()

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun showErrorToast()

    @StateStrategyType(SingleStateStrategy::class)
    fun setFirstData(wallPostList: List<WallPostFull>)

    @StateStrategyType(AddToEndStrategy::class)
    fun setNewData(wallPostList: List<WallPostFull>)

    @StateStrategyType(AddToEndStrategy::class)
    fun setAfterLastData(wallPostList: List<WallPostFull>)
}