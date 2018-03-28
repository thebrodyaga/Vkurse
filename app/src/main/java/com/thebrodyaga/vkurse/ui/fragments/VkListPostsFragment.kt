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
import com.thebrodyaga.vkurse.mvp.presenters.NavigationBarPresenter.Companion.ListPostsFragmentPosition
import com.thebrodyaga.vkurse.mvp.presenters.ScrollToTopPresenter
import com.thebrodyaga.vkurse.mvp.presenters.VkListPostsPresenter
import com.thebrodyaga.vkurse.mvp.views.ScrollToTopView
import com.thebrodyaga.vkurse.mvp.views.VkListPostsView
import com.thebrodyaga.vkurse.ui.adapters.BaseAdapter
import com.thebrodyaga.vkurse.ui.adapters.VkPostsAdapter
import kotlinx.android.synthetic.main.fragment_vk_list_posts.*
import kotlinx.android.synthetic.main.fragment_vk_list_posts.view.*


class VkListPostsFragment : MvpAppCompatFragment(), ScrollToTopView, VkListPostsView, BaseAdapter.OnLoadMoreListener {
    @InjectPresenter(type = PresenterType.GLOBAL, tag = ScrollToTopPresenter.ScrollToTopPresenterTAG)
    lateinit var scrollToTopPresenter: ScrollToTopPresenter
    @InjectPresenter()
    lateinit var vkListPostsPresenter: VkListPostsPresenter
    private lateinit var adapter: VkPostsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_vk_list_posts, container, false)
        view.errorButton.setOnClickListener { vkListPostsPresenter.onErrorButtonClick() }
        view.swipeRefresh.setOnRefreshListener { vkListPostsPresenter.loadNewWall() }
        recyclerView = view.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = VkPostsAdapter(this, recyclerView)
        recyclerView.adapter = adapter
        return view
    }

    override fun scrollTop(menuPosition: Int) {
        if (menuPosition == ListPostsFragmentPosition) {
            Log.d(DEBUG_TAG, "scrollTop VkListPostsFragment")
            recyclerView.scrollToPosition(0)
        }
    }

    override fun setFirstData(wallPostList: List<WallPostFull>) {
        Log.d(DEBUG_TAG, "setFirstData VkListPostsFragment")
        adapter.setToEnd(wallPostList)
    }

    override fun setNewData(wallPostList: List<WallPostFull>) {
        Log.d(DEBUG_TAG, "setNewData VkListPostsFragment")
        adapter.setToStart(wallPostList)
    }

    override fun setAfterLastData(wallPostList: List<WallPostFull>) {
        Log.d(DEBUG_TAG, "setAfterLastData VkListPostsFragment")
        adapter.setToEnd(wallPostList)
    }

    override fun toggleErrorButton(isVisible: Boolean) {
        Log.d(DEBUG_TAG, "toggleErrorButton VkListPostsFragment $isVisible")
        errorButton.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun hideProgressItem() {
        Log.d(DEBUG_TAG, "hideProgressItem VkListPostsFragment")
        adapter.removedProgressItem()
    }

    override fun onLoadMore() {
        vkListPostsPresenter.loadAfterLast()
    }

    override fun hideRefreshing() {
        Log.d(DEBUG_TAG, "hideRefreshing VkListPostsFragment")
        swipeRefresh.isRefreshing = false
    }

    override fun toggleFullScreenProgress(isVisible: Boolean) {
        Log.d(DEBUG_TAG, "toggleFullScreenProgress VkListPostsFragment $isVisible")
        if (isVisible) {
            swipeRefresh.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            swipeRefresh.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    override fun showErrorToast() {
        Log.i(DEBUG_TAG, "showErrorToast")
        Toast.makeText(context, getString(R.string.error_toast), Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val FragmentTAG = "VkListPostsFragmentTAG"
    }
}
