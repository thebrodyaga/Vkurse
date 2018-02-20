package com.example.win10.vkurse.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.example.win10.vkurse.R
import com.example.win10.vkurse.mvp.presenters.ScrollToTopPresenter
import com.example.win10.vkurse.mvp.views.ScrollToTopView


class InstagramListFragment : MvpAppCompatFragment(), ScrollToTopView {
    @InjectPresenter(type = PresenterType.GLOBAL, tag = ScrollToTopPresenter.ScrollToTopPresenterTAG)
    lateinit var scrollToTopPresenter: ScrollToTopPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instagram_list, container, false)
    }

    override fun scrollTop(menuItem: MenuItem) {
        if (menuItem.itemId == R.id.bottom_instagram)
            Log.d("Debug", "scrollTop InstagramListFragment")
    }
}
