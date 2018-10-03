package com.thebrodyaga.vkurse.screen.fragments.groupList


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkobjects.groups.responses.SearchResponse
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.debugLogging
import com.thebrodyaga.vkurse.screen.base.BaseAdapter
import com.thebrodyaga.vkurse.screen.base.BaseFragment
import com.thebrodyaga.vkurse.screen.fragments.groupList.mvp.SearchGroupsPresenter
import com.thebrodyaga.vkurse.screen.fragments.groupList.mvp.SearchGroupsView
import com.thebrodyaga.vkurse.screen.fragments.groupList.mvp.VkListGroupsPresenter
import com.thebrodyaga.vkurse.screen.fragments.groupList.mvp.VkListGroupsView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_vk_list_groups.*
import java.util.concurrent.TimeUnit
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

    @ProvidePresenter
    fun provideListPresenter(): VkListGroupsPresenter = vkListGroupsPresenter

    @ProvidePresenter
    fun provideSearchPresenter(): SearchGroupsPresenter = searchGroupsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = VkGroupsAdapter(this, this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_vk_list_groups, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.main_toolbar, menu)
        RxSearchView.queryTextChanges(menu.findItem(R.id.toolbar_search).actionView as SearchView)
                .skip(1)
                .doOnNext {
                    if (it.isNotEmpty()) vkListGroupsPresenter.getFilteredList(it.toString())
                    else vkListGroupsPresenter.getFullGroups()
                }
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.isNotEmpty()) searchGroupsPresenter.startSearch(it.toString())
                    else searchGroupsPresenter.stopSearch()
                }
    }

    override fun onDestroyView() {
        recyclerView?.adapter = null
        super.onDestroyView()
    }

    //<editor-fold desc="VkListGroupsView">
    override fun showFilteredGroupsList(filteredList: List<Group>) {
        debugLogging( "showFilteredGroupsList")
        adapter.showFilteredList(filteredList)
    }

    override fun showFullGroupsList(fullList: List<Group>) {
        debugLogging( "showFullGroupsList")
        adapter.showFullList(fullList)
    }

    override fun scrollTop() {
        debugLogging( "scrollTop VkListGroupsFragment")
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
        debugLogging( "tootleProgressItem isVisible = $isVisible")
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
        debugLogging( "showErrorToast")
        showToast(getString(R.string.error_toast))
    }
    //</editor-fold>

    override fun onBackPressed() {
        vkListGroupsPresenter.onBackPressed()
    }

    override fun onListItemClick(view: View, position: Int) {
        debugLogging( "onListItemClick")
    }

    companion object {
        const val FragmentTAG = "VkListGroupsFragmentTAG"
    }
}