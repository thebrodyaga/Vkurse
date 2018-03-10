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
import com.thebrodyaga.vkobjects.wall.WallPostFull
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.mvp.presenters.NavigationBarPresenter.Companion.VkFragmentPosition
import com.thebrodyaga.vkurse.mvp.presenters.ScrollToTopPresenter
import com.thebrodyaga.vkurse.mvp.presenters.VkListPresenter
import com.thebrodyaga.vkurse.mvp.views.ScrollToTopView
import com.thebrodyaga.vkurse.mvp.views.VkListView
import com.thebrodyaga.vkurse.ui.adapters.EndlessRecyclerViewScrollListener
import com.thebrodyaga.vkurse.ui.adapters.VkPostsAdapter
import kotlinx.android.synthetic.main.fragment_vk_lists.*
import kotlinx.android.synthetic.main.fragment_vk_lists.view.*


class VkListFragment : MvpAppCompatFragment(), ScrollToTopView, VkListView {

    @InjectPresenter(type = PresenterType.GLOBAL, tag = ScrollToTopPresenter.ScrollToTopPresenterTAG)
    lateinit var scrollToTopPresenter: ScrollToTopPresenter
    @InjectPresenter()
    lateinit var vkListPresenter: VkListPresenter
    private val adapter = VkPostsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_vk_lists, container, false)
        view.errorButton.setOnClickListener { vkListPresenter.onErrorButtonClick() }
        view.swipeRefresh.setOnRefreshListener { vkListPresenter.onSwipeRefresh() }
        view.recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        view.recyclerView.layoutManager = layoutManager
        view.recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                vkListPresenter.loadMore()
            }
        })
        return view
    }

    override fun scrollTop(menuPosition: Int) {
        if (menuPosition == VkFragmentPosition)
            Log.d(DEBUG_TAG, "scrollTop VkListFragment")
    }

    override fun setData(wallPostList: List<WallPostFull>) {
        Log.d(DEBUG_TAG, "setData VkListFragment")
        adapter.setPostToEnd(wallPostList)
    }

    override fun showError() {
        Log.d(DEBUG_TAG, "showError VkListFragment")
        errorButton.visibility = View.VISIBLE
        swipeRefresh.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    override fun onStartLoading() {
        Log.d(DEBUG_TAG, "onStartLoading VkListFragment")
        swipeRefresh.isEnabled = false
    }

    override fun onFinishLoading() {
        Log.d(DEBUG_TAG, "onFinishLoading VkListFragment")
        swipeRefresh.isEnabled = true
    }

    override fun showRefreshing() {
        Log.d(DEBUG_TAG, "showRefreshing VkListFragment")
        swipeRefresh.isRefreshing = true
    }

    override fun hideRefreshing() {
        Log.d(DEBUG_TAG, "hideRefreshing VkListFragment")
        swipeRefresh.isRefreshing = false
    }

    override fun showListProgress() {
        Log.d(DEBUG_TAG, "showListProgress VkListFragment")
        toggleLoading(true)
    }

    override fun hideListProgress() {
        Log.d(DEBUG_TAG, "hideListProgress VkListFragment")
        toggleLoading(false)
    }

    private fun toggleLoading(isLoading: Boolean) {
        if (isLoading) {
            errorButton.visibility = View.GONE
            swipeRefresh.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            errorButton.visibility = View.GONE
            swipeRefresh.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val FragmentTAG = "VkListFragmentTAG"
    }
}
