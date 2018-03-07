package com.thebrodyaga.vkurse.ui.fragments


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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
import com.thebrodyaga.vkurse.ui.adapters.VkPostsAdapter
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
        view.recyclerView.layoutManager = LinearLayoutManager(activity)
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

    companion object {
        const val FragmentTAG = "VkListFragmentTAG"
    }
}
