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
import com.thebrodyaga.vkurse.mvp.views.SearchGroupsView
import com.thebrodyaga.vkurse.ui.adapters.BaseAdapter
import com.thebrodyaga.vkurse.ui.adapters.VkGroupsAdapter
import kotlinx.android.synthetic.main.fragment_vk_list_groups.view.*


class VkListGroupsFragment : MvpAppCompatFragment(), ScrollToTopView, ToolbarSearchView, VkListGroupsView, SearchGroupsView, BaseAdapter.OnLoadMoreListener {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = ScrollToTopPresenter.ScrollToTopPresenterTAG)
    lateinit var scrollToTopPresenter: ScrollToTopPresenter
    @InjectPresenter(type = PresenterType.GLOBAL, tag = ToolbarSearchPresenter.SearchPresenterTAG)
    lateinit var toolbarSearchPresenter: ToolbarSearchPresenter
    @InjectPresenter()
    lateinit var vkListGroupsPresenter: VkListGroupsPresenter
    @InjectPresenter()
    lateinit var searchGroupsPresenter: SearchGroupsPresenter
    private lateinit var adapter: VkGroupsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_vk_list_groups, container, false)
        recyclerView = view.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = VkGroupsAdapter(this)
        recyclerView.adapter = adapter
        return view
    }

    override fun scrollTop(menuPosition: Int) {
        if (menuPosition != NavigationBarPresenter.ListGroupsFragmentPosition) return
        Log.d(DEBUG_TAG, "scrollTop VkListGroupsFragment")
        recyclerView.scrollToPosition(0)
    }

    //<editor-fold desc="VkListGroupsView">
    override fun showFilteredGroupsList(filteredList: List<Group>) {
        Log.i(DEBUG_TAG, "showFilteredGroupsList")
        adapter.showFilteredList(filteredList)
    }

    override fun showFullGroupsList(fullList: List<Group>) {
        Log.i(DEBUG_TAG, "showFullGroupsList")
        adapter.showFullList(fullList)
    }
    //</editor-fold>

    //<editor-fold desc="SearchGroupsView">
    override fun onLoadMore() {
        Log.i("DebugTag", "onLoadMore")
        searchGroupsPresenter.offsetSearchGroups()
    }

    override fun setNewSearchGroup(searchResponse: SearchResponse) {
        Log.i("DebugTag", "setNewSearchGroup")
        adapter.setFirstSearchList(searchResponse.items)
    }

    override fun setOffsetSearchGroup(searchResponse: SearchResponse) {
        Log.i("DebugTag", "setOffsetSearchGroup")
        adapter.setToEnd(searchResponse.items)
    }

    override fun tootleProgressItem(isVisible: Boolean) {
        Log.i(DEBUG_TAG, "tootleProgressItem")
        if (isVisible) adapter.insertProgressItem()
        else adapter.removedProgressItem()
    }

    override fun showErrorToast() {
        Log.i(DEBUG_TAG, "showErrorToast")
        Toast.makeText(this.context, R.string.error_toast, Toast.LENGTH_SHORT).show()
    }

    override fun stopSearch() {
        Log.i(DEBUG_TAG, "stopSearch")
        vkListGroupsPresenter.getFullGroups()
    }
    //</editor-fold>

    //<editor-fold desc="ToolbarSearchView">
    override fun needSearch(query: String) {
        Log.i(DEBUG_TAG, "needSearch VkListGroupsFragment")
        vkListGroupsPresenter.getFilteredList(query)
        searchGroupsPresenter.startSearch(query)
    }

    override fun notNeedSearch() {
        Log.i(DEBUG_TAG, "notNeedSearch VkListGroupsFragment")
        searchGroupsPresenter.stopSearch()
    }
    //</editor-fold>

    companion object {
        const val FragmentTAG = "VkListGroupsFragmentTAG"
    }
}