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
import com.thebrodyaga.vkurse.mvp.presenters.NavigationBarPresenter
import com.thebrodyaga.vkurse.mvp.presenters.ScrollToTopPresenter
import com.thebrodyaga.vkurse.mvp.presenters.ToolbarSearchPresenter
import com.thebrodyaga.vkurse.mvp.views.ScrollToTopView
import com.thebrodyaga.vkurse.mvp.views.ToolbarSearchView


class VkListGroupsFragment : MvpAppCompatFragment(), ScrollToTopView, ToolbarSearchView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = ScrollToTopPresenter.ScrollToTopPresenterTAG)
    lateinit var scrollToTopPresenter: ScrollToTopPresenter
    @InjectPresenter(type = PresenterType.GLOBAL, tag = ToolbarSearchPresenter.SearchPresenterTAG)
    lateinit var toolbarSearchPresenter: ToolbarSearchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vk_list_groups, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun scrollTop(menuPosition: Int) {
        if (menuPosition == NavigationBarPresenter.ListGroupsFragmentPosition)
            Log.d(DEBUG_TAG, "scrollTop VkListGroupsFragment")
    }

    override fun needSearch(query: String) {
        Log.i(DEBUG_TAG, "needSearch VkListGroupsFragment")
    }

    override fun notNeedSearch() {
        Log.i(DEBUG_TAG, "notNeedSearch VkListGroupsFragment")
    }

    companion object {
        const val FragmentTAG = "VkListGroupsFragmentTAG"
    }
}