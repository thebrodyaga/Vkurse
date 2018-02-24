package com.thebrodyaga.vkurse.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.thebrodyaga.vkobjects.wall.WallPostFull
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.mvp.presenters.NavigationBarPresenter.Companion.VkFragmentPosition
import com.thebrodyaga.vkurse.mvp.presenters.ScrollToTopPresenter
import com.thebrodyaga.vkurse.mvp.presenters.VkListPresenter
import com.thebrodyaga.vkurse.mvp.views.ScrollToTopView
import com.thebrodyaga.vkurse.mvp.views.VkListView


class VkListFragment : MvpAppCompatFragment(), ScrollToTopView,VkListView {
    @InjectPresenter(type = PresenterType.GLOBAL, tag = ScrollToTopPresenter.ScrollToTopPresenterTAG)
    lateinit var scrollToTopPresenter: ScrollToTopPresenter
    @InjectPresenter()
    lateinit var vkListPresenter: VkListPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vk_lists, container, false)
    }

    override fun scrollTop(menuPosition: Int) {
        if (menuPosition == VkFragmentPosition)
            Log.d("Debug", "scrollTop VkListFragment")
    }

    override fun setData(wallPostList: List<WallPostFull>) {
        Log.d("Debug", "setData VkListFragment")
    }
}
