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
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.mvp.presenters.ToolbarSearchPresenter
import com.thebrodyaga.vkurse.mvp.views.ToolbarSearchView


class VkListToolbarSearchGroupsFragment : MvpAppCompatFragment(), ToolbarSearchView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = ToolbarSearchPresenter.SearchPresenterTAG)
    lateinit var toolbarSearchPresenter: ToolbarSearchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vk_list_search_groups, container, false)
    }

    override fun needSearch(query: String) {
        Log.i(DEBUG_TAG, "needSearch VkListToolbarSearchGroupsFragment")
    }
}
