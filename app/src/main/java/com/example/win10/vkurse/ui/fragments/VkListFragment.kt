package com.example.win10.vkurse.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.example.win10.vkurse.R
import com.example.win10.vkurse.mvp.presenters.NavigationBarPresenter.Companion.VkFragmentPosition
import com.example.win10.vkurse.mvp.presenters.ScrollToTopPresenter
import com.example.win10.vkurse.mvp.views.ScrollToTopView


class VkListFragment : MvpAppCompatFragment(), ScrollToTopView {
    @InjectPresenter(type = PresenterType.GLOBAL, tag = ScrollToTopPresenter.ScrollToTopPresenterTAG)
    lateinit var scrollToTopPresenter: ScrollToTopPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vk_lists, container, false)
    }

    override fun scrollTop(menuPosition: Int) {
        if (menuPosition == VkFragmentPosition)
            Log.d("Debug", "scrollTop VkListFragment")
    }
}
