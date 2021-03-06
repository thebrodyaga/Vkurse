package com.thebrodyaga.vkurse.screen.fragments.postList


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.debugLogging
import com.thebrodyaga.vkurse.domain.entities.ui.ItemModel
import com.thebrodyaga.vkurse.domain.entities.ui.postList.ItemsForPostList
import com.thebrodyaga.vkurse.screen.adapters.VkPostsAdapter
import com.thebrodyaga.vkurse.screen.base.BaseFragment
import com.thebrodyaga.vkurse.screen.fragments.postList.mvp.VkListPostsPresenter
import com.thebrodyaga.vkurse.screen.fragments.postList.mvp.VkListPostsView
import com.thebrodyaga.vkurse.screen.utils.EndlessRecyclerOnScrollListener
import com.thebrodyaga.vkurse.screen.utils.decorations.VerticalOffsetItemDecoration
import kotlinx.android.synthetic.main.fragment_vk_list_posts.*
import javax.inject.Inject


class VkListPostsFragment : BaseFragment(), VkListPostsView {
    override fun updateList(searchResponse: List<ItemModel<ItemsForPostList>>, isScrollToTop: Boolean) {
        debugLogging("updateList")
        adapter.submitList(ArrayList(searchResponse))
        if (isScrollToTop && searchResponse.isNotEmpty()) recyclerView.smoothScrollToPosition(0)
    }

    @Inject
    @InjectPresenter()
    lateinit var presenter: VkListPostsPresenter
    private val adapter = VkPostsAdapter()

    @ProvidePresenter
    fun providePresenter(): VkListPostsPresenter = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_vk_list_posts, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorButton.setOnClickListener { presenter.onErrorButtonClick() }
        swipeRefresh.setOnRefreshListener { presenter.loadNewWall() }
        bindRecyclerView(recyclerView)
    }

    private fun bindRecyclerView(recyclerView: RecyclerView) {
        debugLogging("setupRecyclerView")
        val layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val scrollListener = EndlessRecyclerOnScrollListener(layoutManager)
        scrollListener.setOnLoadMore { presenter.loadAfterLast() }
        recyclerView.addOnScrollListener(scrollListener)
        recyclerView.addItemDecoration(VerticalOffsetItemDecoration())
        adapter.setOnListItemClickListener { item, position, _ -> }
    }

    override fun hideRefreshing() {
        debugLogging("hideRefreshing VkListPostsFragment")
        swipeRefresh.isRefreshing = false
    }

    override fun choiceForegroundView(viewFlag: String) {
        debugLogging("choiceForegroundView VkListPostsFragment $viewFlag")
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
        debugLogging("showErrorToast")
        showToast(getString(R.string.error_toast))
    }

    override fun scrollTop() {
        debugLogging("scrollTop VkListPostsFragment")
        recyclerView?.scrollToPosition(0)
    }

    companion object {
        const val FragmentTAG = "VkListPostsFragmentTAG"
    }
}
