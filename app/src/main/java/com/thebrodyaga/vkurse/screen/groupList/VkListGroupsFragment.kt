package com.thebrodyaga.vkurse.screen.groupList


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkobjects.groups.responses.SearchResponse
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.screen.base.BaseAdapter
import com.thebrodyaga.vkurse.screen.base.BaseFragment
import com.thebrodyaga.vkurse.screen.groupList.mvp.SearchGroupsPresenter
import com.thebrodyaga.vkurse.screen.groupList.mvp.SearchGroupsView
import com.thebrodyaga.vkurse.screen.groupList.mvp.VkListGroupsPresenter
import com.thebrodyaga.vkurse.screen.groupList.mvp.VkListGroupsView
import kotlinx.android.synthetic.main.fragment_vk_list_groups.view.*
import javax.inject.Inject


class VkListGroupsFragment : BaseFragment(), VkListGroupsView,
        SearchGroupsView, BaseAdapter.OnLoadMoreListener, BaseAdapter.OnItemClickListener {

    @Inject
    @InjectPresenter()
    lateinit var vkListGroupsPresenter: VkListGroupsPresenter
    @Inject
    @InjectPresenter()
    lateinit var searchGroupsPresenter: SearchGroupsPresenter
    private lateinit var adapter: VkGroupsAdapter
    private var recyclerView: RecyclerView? = null

    @ProvidePresenter
    fun provideListPresenter(): VkListGroupsPresenter = vkListGroupsPresenter

    @ProvidePresenter
    fun provideSearchPresenter(): SearchGroupsPresenter = searchGroupsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = VkGroupsAdapter(this,this)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_vk_list_groups, container, false)
        recyclerView = view.recyclerView
        recyclerView?.layoutManager = LinearLayoutManager(inflater.context)
        recyclerView?.adapter = adapter
        return view
    }

    override fun onDestroyView() {
        recyclerView?.adapter = null
        super.onDestroyView()
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

    override fun scrollTop() {
        Log.d(DEBUG_TAG, "scrollTop VkListGroupsFragment")
        recyclerView?.scrollToPosition(0)
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
        Log.i(DEBUG_TAG, "tootleProgressItem isVisible = $isVisible")
        if (isVisible) adapter.insertProgressItem()
        else adapter.removedProgressItem()
    }

    override fun clearSearchList() {
        Log.i("DebugTag", "clearSearchList")
        adapter.clearSearchList()
        adapter.removeHeader()
    }


    override fun showTextHeader() {
        Log.i("DebugTag", "showTextHeader")
        adapter.showTextHeader()
    }

    override fun showProgressHeader() {
        Log.i("DebugTag", "showProgressHeader")
        adapter.showProgressHeader()
    }

    override fun showErrorToast() {
        Log.i(DEBUG_TAG, "showErrorToast")
        showToast(getString(R.string.error_toast))
    }
    //</editor-fold>

    override fun onListItemClick(view: View, position: Int) {
        Log.d(DEBUG_TAG, "onListItemClick")
    }

    companion object {
        const val FragmentTAG = "VkListGroupsFragmentTAG"
    }
}