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
import com.thebrodyaga.vkobjects.groups.responses.SearchResponse
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.mvp.presenters.ToolbarSearchPresenter
import com.thebrodyaga.vkurse.mvp.presenters.VkListSearchGroupsPresenter
import com.thebrodyaga.vkurse.mvp.views.ToolbarSearchView
import com.thebrodyaga.vkurse.mvp.views.VkListSearchGroupsView
import com.thebrodyaga.vkurse.ui.adapters.BaseAdapter
import com.thebrodyaga.vkurse.ui.adapters.VkGroupsAdapter
import kotlinx.android.synthetic.main.fragment_vk_list_posts.view.*
import kotlinx.android.synthetic.main.fragment_vk_list_search_groups.*


class VkListSearchGroupsFragment : MvpAppCompatFragment(), ToolbarSearchView, VkListSearchGroupsView, BaseAdapter.OnLoadMoreListener {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = ToolbarSearchPresenter.SearchPresenterTAG)
    lateinit var toolbarSearchPresenter: ToolbarSearchPresenter
    @InjectPresenter()
    lateinit var vkListSearchGroupsPresenter: VkListSearchGroupsPresenter
    private lateinit var adapter: VkGroupsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_vk_list_search_groups, container, false)
        recyclerView = view.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = VkGroupsAdapter(this, recyclerView)
        recyclerView.adapter = adapter
        return view
    }

    //<editor-fold desc="ToolbarSearchView">
    override fun needSearch(query: String) {
        Log.i(DEBUG_TAG, "needSearch VkListSearchGroupsFragment")
        vkListSearchGroupsPresenter.startSearch(query)
    }

    override fun notNeedSearch() {
        Log.i(DEBUG_TAG, "notNeedSearch VkListSearchGroupsFragment")
        adapter.clearList()
        vkListSearchGroupsPresenter.stopSearch()
    }
    //</editor-fold>

    //<editor-fold desc="VkListSearchGroupsView">
    override fun setNewResult(searchResponse: SearchResponse) {
        Log.i(DEBUG_TAG, "setNewResult")
        adapter.clearList()
        adapter.setToEnd(searchResponse.items)
    }

    override fun setOffsetResult(searchResponse: SearchResponse) {
        Log.i(DEBUG_TAG, "setOffsetResult")
        adapter.setToEnd(searchResponse.items)
    }

    override fun toggleProgress(isVisible: Boolean) {
        Log.i(DEBUG_TAG, "toggleProgress isVisible = $isVisible")
        progressBar.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        resultLayout.visibility = if (isVisible) View.INVISIBLE else View.VISIBLE
    }

    override fun toggleSearchFragment(isVisible: Boolean) {
        Log.i(DEBUG_TAG, "toggleSearchFragment isVisible = $isVisible")
        val fragmentManager = parentFragment?.childFragmentManager ?: return
        val fragment = fragmentManager.findFragmentById(R.id.searchGroupsFragment) ?: return
        if (isVisible) fragmentManager.beginTransaction().show(fragment).commit()
        else fragmentManager.beginTransaction().hide(fragment).commit()
    }

    override fun showErrorToast() {
        Log.i(DEBUG_TAG, "showErrorToast")
        Toast.makeText(context, getString(R.string.error_toast), Toast.LENGTH_SHORT).show()
    }

    override fun hideProgressItem() {
        Log.i(DEBUG_TAG, "hideProgressItem")
        adapter.removedProgressItem()
    }
    //</editor-fold>

    override fun onLoadMore() {
        Log.i(DEBUG_TAG, "onLoadMore VkListSearchGroupsFragment")
        vkListSearchGroupsPresenter.offsetSearchGroups()
    }
}
