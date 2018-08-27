package com.thebrodyaga.vkurse.screen.postList


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thebrodyaga.vkobjects.wall.WallPostFull
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.screen.base.BaseAdapter
import com.thebrodyaga.vkurse.screen.base.BaseFragment
import com.thebrodyaga.vkurse.screen.postList.mvp.VkListPostsPresenter
import com.thebrodyaga.vkurse.screen.postList.mvp.VkListPostsView
import kotlinx.android.synthetic.main.fragment_vk_list_posts.*
import kotlinx.android.synthetic.main.fragment_vk_list_posts.view.*
import javax.inject.Inject


class VkListPostsFragment : BaseFragment(), VkListPostsView, BaseAdapter.OnLoadMoreListener {

    @Inject
    @InjectPresenter()
    lateinit var presenter: VkListPostsPresenter
    private lateinit var adapter: VkPostsAdapter
    private var recyclerView: RecyclerView? = null

    @ProvidePresenter
    fun providePresenter(): VkListPostsPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = VkPostsAdapter(this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_vk_list_posts, container, false)
        view.errorButton.setOnClickListener { presenter.onErrorButtonClick() }
        view.swipeRefresh.setOnRefreshListener { presenter.loadNewWall() }
        recyclerView = view.recyclerView
        recyclerView?.layoutManager = LinearLayoutManager(inflater.context)
        recyclerView?.adapter = adapter
        return view
    }

    override fun onDestroyView() {
        recyclerView?.adapter = null
        super.onDestroyView()
    }

    override fun setFirstData(wallPostList: List<WallPostFull>) {
        Log.d(DEBUG_TAG, "setFirstData VkListPostsFragment")
        adapter.clearList()
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

    override fun tootleProgressItem(isVisible: Boolean) {
        Log.d(DEBUG_TAG, "tootleProgressItem VkListPostsFragment isVisible = $isVisible")
        if (isVisible) adapter.insertProgressItem()
        else adapter.removedProgressItem()
    }

    override fun onLoadMore() {
        presenter.loadAfterLast()
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
        /*VkListPostsPresenter.EMPTY_VIEW_FLAG -> emptyButton.visibility = View.VISIBLE*/
        }
    }

    override fun showErrorToast() {
        Log.i(DEBUG_TAG, "showErrorToast")
        showToast(getString(R.string.error_toast))
    }

    override fun scrollTop() {
        Log.d(DEBUG_TAG, "scrollTop VkListPostsFragment")
        recyclerView?.scrollToPosition(0)
    }

    companion object {
        const val FragmentTAG = "VkListPostsFragmentTAG"
    }
}
