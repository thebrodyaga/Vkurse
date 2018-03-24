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
import com.thebrodyaga.vkobjects.wall.WallPostFull
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.mvp.presenters.NavigationBarPresenter.Companion.VkFragmentPosition
import com.thebrodyaga.vkurse.mvp.presenters.ScrollToTopPresenter
import com.thebrodyaga.vkurse.mvp.presenters.VkListPresenter
import com.thebrodyaga.vkurse.mvp.views.ScrollToTopView
import com.thebrodyaga.vkurse.mvp.views.VkListView
import com.thebrodyaga.vkurse.ui.adapters.VkPostsAdapter
import kotlinx.android.synthetic.main.fragment_vk_lists.*
import kotlinx.android.synthetic.main.fragment_vk_lists.view.*


class VkListFragment : MvpAppCompatFragment(), ScrollToTopView, VkListView, VkPostsAdapter.OnLoadMoreListener {
    @InjectPresenter(type = PresenterType.GLOBAL, tag = ScrollToTopPresenter.ScrollToTopPresenterTAG)
    lateinit var scrollToTopPresenter: ScrollToTopPresenter
    @InjectPresenter()
    lateinit var vkListPresenter: VkListPresenter
    private lateinit var adapter: VkPostsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_vk_lists, container, false)
        view.errorButton.setOnClickListener { vkListPresenter.onErrorButtonClick() }
        view.swipeRefresh.setOnRefreshListener { vkListPresenter.loadNewWall() }
        recyclerView = view.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = VkPostsAdapter(this, recyclerView)
        recyclerView.adapter = adapter
        return view
    }

    override fun scrollTop(menuPosition: Int) {
        if (menuPosition == VkFragmentPosition) {
            Log.d(DEBUG_TAG, "scrollTop VkListFragment")
            recyclerView.scrollToPosition(0)
        }
    }

    override fun setFirstData(wallPostList: List<WallPostFull>) {
        Log.d(DEBUG_TAG, "setFirstData VkListFragment")
        adapter.setPostToEnd(wallPostList)
    }

    override fun setNewData(wallPostList: List<WallPostFull>) {
        Log.d(DEBUG_TAG, "setNewData VkListFragment")
        adapter.setPostToStart(wallPostList)
    }

    override fun setAfterLastData(wallPostList: List<WallPostFull>) {
        Log.d(DEBUG_TAG, "setAfterLastData VkListFragment")
        adapter.setPostToEnd(wallPostList)
    }

    override fun toggleErrorButton(isVisible: Boolean) {
        Log.d(DEBUG_TAG, "toggleErrorButton VkListFragment $isVisible")
        errorButton.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun hideProgressItem() {
        Log.d(DEBUG_TAG, "hideProgressItem VkListFragment")
        adapter.removedProgressItem()
    }

    override fun onLoadMore() {
        vkListPresenter.loadAfterLast()
    }

    override fun hideRefreshing() {
        Log.d(DEBUG_TAG, "hideRefreshing VkListFragment")
        swipeRefresh.isRefreshing = false
    }

    override fun toggleFullScreenProgress(isVisible: Boolean) {
        Log.d(DEBUG_TAG, "toggleFullScreenProgress VkListFragment $isVisible")
        if (isVisible) {
            swipeRefresh.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            swipeRefresh.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    override fun showErrorToast() {
        Toast.makeText(context, "Проблемы с сетью", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val FragmentTAG = "VkListFragmentTAG"
    }
}
