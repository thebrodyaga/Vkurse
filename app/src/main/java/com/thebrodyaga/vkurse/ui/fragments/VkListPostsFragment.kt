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

    override fun choiceForegroundView(viewFlag: String) {
        Log.d(DEBUG_TAG, "choiceForegroundView VkListPostsFragment $viewFlag")
        swipeRefresh.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorButton.visibility = View.GONE
        when (viewFlag) {
            VkListPostsPresenter.DATA_VIEW_FLAG -> swipeRefresh.visibility = View.VISIBLE
            VkListPostsPresenter.PROGRESS_VIEW_FLAG -> progressBar.visibility = View.VISIBLE
            VkListPostsPresenter.ERROR_VIEW_FLAG -> errorButton.visibility = View.VISIBLE
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
