package com.thebrodyaga.vkurse.ui.fragments


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.mvp.presenters.NavigationBarPresenter
import com.thebrodyaga.vkurse.mvp.presenters.ScrollToTopPresenter
import com.thebrodyaga.vkurse.mvp.presenters.ToolbarSearchPresenter
import com.thebrodyaga.vkurse.mvp.presenters.VkListGroupsPresenter
import com.thebrodyaga.vkurse.mvp.views.ScrollToTopView
import com.thebrodyaga.vkurse.mvp.views.ToolbarSearchView
import com.thebrodyaga.vkurse.mvp.views.VkListGroupsView
import com.thebrodyaga.vkurse.ui.adapters.VkGroupsAdapter
import kotlinx.android.synthetic.main.fragment_vk_list_groups.view.*


class VkListGroupsFragment : MvpAppCompatFragment(), ScrollToTopView, ToolbarSearchView, VkListGroupsView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = ScrollToTopPresenter.ScrollToTopPresenterTAG)
    lateinit var scrollToTopPresenter: ScrollToTopPresenter
    @InjectPresenter(type = PresenterType.GLOBAL, tag = ToolbarSearchPresenter.SearchPresenterTAG)
    lateinit var toolbarSearchPresenter: ToolbarSearchPresenter
    @InjectPresenter()
    lateinit var vkListGroupsPresenter: VkListGroupsPresenter
    private lateinit var adapter: VkGroupsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_vk_list_groups, container, false)
        recyclerView = view.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = VkGroupsAdapter(null, recyclerView)
        recyclerView.adapter = adapter
        return view
    }

    override fun scrollTop(menuPosition: Int) {
        if (menuPosition == NavigationBarPresenter.ListGroupsFragmentPosition)
            Log.d(DEBUG_TAG, "scrollTop VkListGroupsFragment")
    }

    //<editor-fold desc="VkListGroupsView">
    override fun setFavoriteGroups(groups: List<Group>) {
        Log.i(DEBUG_TAG, "setFavoriteGroups")
        adapter.setToEnd(groups)
    }
    //</editor-fold>

    //<editor-fold desc="ToolbarSearchView">
    override fun needSearch(query: String) {
        Log.i(DEBUG_TAG, "needSearch VkListGroupsFragment")
    }

    override fun notNeedSearch() {
        Log.i(DEBUG_TAG, "notNeedSearch VkListGroupsFragment")
    }
    //</editor-fold>

    companion object {
        const val FragmentTAG = "VkListGroupsFragmentTAG"
    }
}