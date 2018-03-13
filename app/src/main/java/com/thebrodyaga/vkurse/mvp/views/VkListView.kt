package com.thebrodyaga.vkurse.mvp.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thebrodyaga.vkobjects.wall.WallPostFull


/**
 * Created by Emelyanov.N4 on 22.02.2018.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface VkListView : MvpView {

    fun showError()

    fun onStartLoading()

    fun onFinishLoading()

    fun showRefreshing()

    fun hideRefreshing()

    fun showListProgress()

    fun hideListProgress()

    fun hideProgressItem()

    @StateStrategyType(AddToEndStrategy::class)
    fun setData(wallPostList: List<WallPostFull>)
}