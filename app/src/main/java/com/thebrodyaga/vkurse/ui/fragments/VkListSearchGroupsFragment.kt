package com.thebrodyaga.vkurse.ui.fragments


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.thebrodyaga.vkobjects.groups.responses.SearchResponse

import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.mvp.presenters.ToolbarSearchPresenter
import com.thebrodyaga.vkurse.mvp.presenters.VkListSearchGroupsPresenter
import com.thebrodyaga.vkurse.mvp.views.ToolbarSearchView
import com.thebrodyaga.vkurse.mvp.views.VkListSearchGroupsView


class VkListSearchGroupsFragment : MvpAppCompatFragment(), ToolbarSearchView,VkListSearchGroupsView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = ToolbarSearchPresenter.SearchPresenterTAG)
    lateinit var toolbarSearchPresenter: ToolbarSearchPresenter
    @InjectPresenter()
    lateinit var vkListSearchGroupsPresenter: VkListSearchGroupsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vk_list_search_groups, container, false)
    }

    override fun needSearch(query: String) {
        Log.i(DEBUG_TAG, "needSearch VkListSearchGroupsFragment")
        vkListSearchGroupsPresenter.startHandler(query)
    }
    override fun setNewResult(searchResponse: SearchResponse) {
        Log.i(DEBUG_TAG, "setNewResult")
    }

    override fun setOffsetResult(searchResponse: SearchResponse) {
        Log.i(DEBUG_TAG, "setOffsetResult")
    }

    override fun toggleProgress(isVisible: Boolean) {
        Log.i(DEBUG_TAG, "toggleProgress")
    }

    override fun showErrorToast() {
        Log.i(DEBUG_TAG, "showErrorToast")
    }

    override fun hideProgressItem() {
        Log.i(DEBUG_TAG, "hideProgressItem")
    }
}
