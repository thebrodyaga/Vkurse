package com.thebrodyaga.vkurse.ui.fragments


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkobjects.groups.responses.SearchResponse
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.mvp.presenters.*
import com.thebrodyaga.vkurse.mvp.views.ScrollToTopView
import com.thebrodyaga.vkurse.mvp.views.ToolbarSearchView
import com.thebrodyaga.vkurse.mvp.views.VkListGroupsView
import com.thebrodyaga.vkurse.mvp.views.VkListSearchGroupsView
import com.thebrodyaga.vkurse.ui.adapters.BaseAdapter
import com.thebrodyaga.vkurse.ui.adapters.VkGroupsAdapter
import kotlinx.android.synthetic.main.fragment_vk_list_groups.view.*


class VkListGroupsFragment : MvpAppCompatFragment(), ScrollToTopView, ToolbarSearchView, VkListGroupsView, VkListSearchGroupsView, BaseAdapter.OnLoadMoreListener {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = ScrollToTopPresenter.ScrollToTopPresenterTAG)
    lateinit var scrollToTopPresenter: ScrollToTopPresenter
    @InjectPresenter(type = PresenterType.GLOBAL, tag = ToolbarSearchPresenter.SearchPresenterTAG)
    lateinit var toolbarSearchPresenter: ToolbarSearchPresenter
    @InjectPresenter()
    lateinit var vkListGroupsPresenter: VkListGroupsPresenter
    @InjectPresenter()
    lateinit var vkListSearchGroupsPresenter: VkListSearchGroupsPresenter
    private lateinit var adapter: VkGroupsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_vk_list_groups, container, false)
        recyclerView = view.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = VkGroupsAdapter(this, recyclerView)
        recyclerView.adapter = adapter
        return view
    }

    override fun scrollTop(menuPosition: Int) {
        if (menuPosition != NavigationBarPresenter.ListGroupsFragmentPosition) return
        Log.d(DEBUG_TAG, "scrollTop VkListGroupsFragment")
        recyclerView.scrollToPosition(0)
    }

    //<editor-fold desc="VkListGroupsView">
    override fun setFavoriteGroups(groups: List<Group>) {
        Log.i(DEBUG_TAG, "setFavoriteGroups")
        adapter.showFullList(groups)
    }
    //</editor-fold>

    override fun onLoadMore() {
        Log.i("DebugTag", "onLoadMore")
        vkListSearchGroupsPresenter.offsetSearchGroups()
    }

    override fun toggleSearchFragment(isVisible: Boolean) {
        Log.i("DebugTag", "toggleSearchFragment")
    }

    override fun setNewResult(searchResponse: SearchResponse) {
        Log.i("DebugTag", "setNewResult")
        adapter.setFirstSearchList(searchResponse.items)
    }

    override fun setOffsetResult(searchResponse: SearchResponse) {
        Log.i("DebugTag", "setOffsetResult")
        adapter.setToEnd(searchResponse.items)
    }

    override fun toggleProgress(isVisible: Boolean) {
        Log.i("DebugTag", "toggleProgress")
    }

    override fun showErrorToast() {
        Log.i(DEBUG_TAG, "showErrorToast")
        Toast.makeText(context, getString(R.string.error_toast), Toast.LENGTH_SHORT).show()
    }

    override fun hideProgressItem() {
        Log.i(DEBUG_TAG, "hideProgressItem")
        adapter.removedProgressItem()
    }

    //<editor-fold desc="ToolbarSearchView">
    override fun needSearch(query: String) {
        Log.i(DEBUG_TAG, "needSearch VkListGroupsFragment")
        adapter.filteredList(query)
        vkListSearchGroupsPresenter.startSearch(query)
    }

    override fun notNeedSearch() {
        Log.i(DEBUG_TAG, "notNeedSearch VkListGroupsFragment")
        adapter.showFullList()
        vkListSearchGroupsPresenter.stopSearch()
    }
    //</editor-fold>

    companion object {
        const val FragmentTAG = "VkListGroupsFragmentTAG"
    }
}