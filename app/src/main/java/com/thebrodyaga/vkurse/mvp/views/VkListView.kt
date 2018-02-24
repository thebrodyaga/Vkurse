package com.thebrodyaga.vkurse.mvp.views

import com.arellomobile.mvp.MvpView
import com.thebrodyaga.vkobjects.wall.WallPostFull


/**
 * Created by Emelyanov.N4 on 22.02.2018.
 */
interface VkListView : MvpView {
    fun setData(wallPostList: List<WallPostFull>)
}