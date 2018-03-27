package com.thebrodyaga.vkurse.ui.adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */


abstract class BaseAdapter<T>(private val onLoadMoreListener: OnLoadMoreListener?,
                              recyclerView: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * если onLoadMoreListener = null для новые данный не грузит
     */
    init {
        val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager
        if (linearLayoutManager != null && onLoadMoreListener != null) recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isLoading) return
                if (linearLayoutManager.itemCount
                        <= linearLayoutManager.findLastVisibleItemPosition() + visibleThreshold) {
                    contentList.add(null)
                    recyclerView?.post({ notifyItemInserted(contentList.size - 1) })
                    onLoadMoreListener.onLoadMore()
                    isLoading = true
                }
            }
        })
    }

    @Inject
    protected lateinit var picasso: Picasso
    protected val contentList = arrayListOf<T?>()
    protected val visibleThreshold = 2    //последний видимый перед загрузкой
    protected var isLoading: Boolean = false

    override fun getItemCount(): Int {
        return contentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (contentList[position] != null) VIEW_ITEM else VIEW_PROGRESS
    }

    class ProgressHolder(containerView: View) : RecyclerView.ViewHolder(containerView)

    companion object {
        const val VIEW_ITEM = 1
        const val VIEW_PROGRESS = 0
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }
}