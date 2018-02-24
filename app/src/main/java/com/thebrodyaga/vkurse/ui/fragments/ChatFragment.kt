package com.thebrodyaga.vkurse.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.mvp.presenters.NavigationBarPresenter.Companion.ChatFragmentPosition
import com.thebrodyaga.vkurse.mvp.presenters.ScrollToTopPresenter
import com.thebrodyaga.vkurse.mvp.views.ScrollToTopView


class ChatFragment : MvpAppCompatFragment(), ScrollToTopView {
    @InjectPresenter(type = PresenterType.GLOBAL, tag = ScrollToTopPresenter.ScrollToTopPresenterTAG)
    lateinit var scrollToTopPresenter: ScrollToTopPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun scrollTop(menuPosition: Int) {
        if (menuPosition == ChatFragmentPosition)
            Log.d("Debug", "scrollTop ChatFragment")
    }
}
