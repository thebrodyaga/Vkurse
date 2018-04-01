package com.thebrodyaga.vkurse.ui.adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.thebrodyaga.vkurse.R

/**
 * Created by Emelyanov.N4
 *         on 30.03.2018
 */

abstract class SearchAdapter<T>(onLoadMoreListener: OnLoadMoreListener?,
                                recyclerView: RecyclerView) : BaseAdapter<T>(null, recyclerView) {
    init {
        val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager
        if (linearLayoutManager != null && onLoadMoreListener != null) recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            //TODO из-за position в notifyItemInserted дубль в init
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy <= 0 || isLoading || contentList.isEmpty()) return
                if (linearLayoutManager.itemCount
                        == linearLayoutManager.findLastVisibleItemPosition() + visibleThreshold) {
                    isLoading = true
                    contentList.add(null)
                    recyclerView?.post({ notifyItemInserted(contentList.size + visibleList.size) })
                    onLoadMoreListener.onLoadMore()
                }
            }
        })
    }

    protected val visibleList = arrayListOf<T>()
    protected val fullList = arrayListOf<T>()
    private var currentState = EMPTY_LIST_STEP

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_HEADER_PROGRESS -> {
                val progressBar = LayoutInflater.from(parent.context)
                        .inflate(R.layout.middle_progress_bar, parent, false) as FrameLayout
                progressBar.setPadding(0, 12, 0, 0)
                HeaderProgressHolder(progressBar)
            }
            VIEW_HEADER -> HeaderHolder(LayoutInflater.from(parent.context).inflate(R.layout.sub_headers_text, parent, false))
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun getItemCount(): Int {
        return when (currentState) {
            DEVICE_LIST_STEP -> visibleList.size
            NEW_SEARCH_STEP, DEVICE_AND_SEARCH_LIST_STEP -> visibleList.size + 1 + contentList.size
            else -> 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentState) {
            DEVICE_LIST_STEP -> VIEW_ITEM
            NEW_SEARCH_STEP ->
                if (position == visibleList.size) VIEW_HEADER_PROGRESS else VIEW_ITEM
            DEVICE_AND_SEARCH_LIST_STEP -> {
                when (position) {
                    in 0 until visibleList.size -> VIEW_ITEM
                    visibleList.size -> VIEW_HEADER
                    else -> if (contentList[position - visibleList.size - 1] == null) VIEW_PROGRESS
                    else VIEW_ITEM
                }
            }
            else -> VIEW_ITEM
        }
    }

    open fun filteredList(query: String) {
        currentState = NEW_SEARCH_STEP
        clearSearchList()
    }

    private fun clearSearchList() {
        removedProgressItem()
        val itemCount = contentList.size
        contentList.clear()
        if (itemCount != 0) notifyItemRangeRemoved(visibleList.size + 1, itemCount)
    }

    private fun updateFullList(list: List<T>) {
        fullList.clear()
        fullList.addAll(list)
    }

    fun showFullList(list: List<T>? = null) {
        currentState = DEVICE_LIST_STEP
        visibleList.clear()
        visibleList.addAll(list ?: fullList)
        removedProgressItem()
        if (list != null) updateFullList(list)
        contentList.clear()
        notifyDataSetChanged()
    }

    override fun setToEnd(contentList: List<T>) {
        removedProgressItem()
        val itemCount = this.contentList.size
        this.contentList.addAll(contentList)
        notifyItemRangeInserted(visibleList.size + itemCount + 1, contentList.size)
    }

    fun setFirstSearchList(contentList: List<T>) {
        currentState = DEVICE_AND_SEARCH_LIST_STEP
        notifyItemChanged(visibleList.size + 1)
        val itemCount = visibleList.size
        this.contentList.clear()
        if (itemCount != 0) notifyItemRangeRemoved(visibleList.size + 1, itemCount)
        this.contentList.addAll(contentList)
        notifyItemRangeInserted(visibleList.size + 1, contentList.size)
    }

    override fun clearList() {
        currentState = EMPTY_LIST_STEP
        visibleList.clear()
        fullList.clear()
        super.clearList()
    }

    override fun removedProgressItem() {
        if (!isLoading || contentList.isEmpty()) return
        contentList.removeAt(contentList.size - 1)
        notifyItemRemoved(contentList.size + 1 + visibleList.size)
        isLoading = false
    }

    /**
     * visibleList список из базы, contentList из сети
     * DEVICE_LIST_STEP список из базы, NEW_SEARCH_STEP фильтрованный список из базы и прогресс заголовок
     * DEVICE_AND_SEARCH_LIST_STEP два списка разделенные заголовком
     */

    companion object {
        const val VIEW_HEADER = 2
        const val VIEW_HEADER_PROGRESS = 3
        private const val DEVICE_LIST_STEP = "deviceListStep"
        private const val NEW_SEARCH_STEP = "newSearchStep"
        private const val DEVICE_AND_SEARCH_LIST_STEP = "deviceAndSearchListStep"
        private const val EMPTY_LIST_STEP = "emptyListStep"
    }

    class HeaderProgressHolder(containerView: View) : RecyclerView.ViewHolder(containerView)

    class HeaderHolder(containerView: View) : RecyclerView.ViewHolder(containerView)

}