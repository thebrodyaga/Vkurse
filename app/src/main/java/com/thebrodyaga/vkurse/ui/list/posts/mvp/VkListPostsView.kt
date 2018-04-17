package com.thebrodyaga.vkurse.ui.list.posts.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.thebrodyaga.vkobjects.wall.WallPostFull


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

    @StateStrategyType(SingleStateStrategy::class)
    fun setFirstData(wallPostList: List<WallPostFull>)

    @StateStrategyType(AddToEndStrategy::class)
    fun setNewData(wallPostList: List<WallPostFull>)

    @StateStrategyType(AddToEndStrategy::class)
    fun setAfterLastData(wallPostList: List<WallPostFull>)
}