package com.thebrodyaga.vkurse.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.thebrodyaga.vkurse.R

/**
 * Created by Emelyanov.N4
 *         on 30.03.2018
 */

abstract class SearchAdapter<T>(onLoadMoreListener: OnLoadMoreListener?) : BaseAdapter<T>(onLoadMoreListener) {

    protected val mainList = arrayListOf<T>()
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
            DEVICE_LIST_STEP -> mainList.size
            NEW_SEARCH_STEP, DEVICE_AND_SEARCH_LIST_STEP -> mainList.size + 1 + contentList.size
            else -> 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentState) {
            DEVICE_LIST_STEP -> VIEW_ITEM
            NEW_SEARCH_STEP ->
                if (position == mainList.size) VIEW_HEADER_PROGRESS else VIEW_ITEM
            DEVICE_AND_SEARCH_LIST_STEP -> {
                when (position) {
                    in 0 until mainList.size -> VIEW_ITEM
                    mainList.size -> VIEW_HEADER
                    else -> if (contentList[position - mainList.size - 1] == null) VIEW_PROGRESS
                    else VIEW_ITEM
                }
            }
            else -> VIEW_ITEM
        }
    }

    fun showFullList(list: List<T>) {
        clearSearchList()
        currentState = DEVICE_LIST_STEP
        notifyItemRemoved(itemCount)
        refreshMainList(list)
    }

    fun showFilteredList(list: List<T>) {
        currentState = NEW_SEARCH_STEP
        clearSearchList()
        notifyItemChanged(itemCount)
        refreshMainList(list)
    }

    fun setFirstSearchList(contentList: List<T>) {
        currentState = DEVICE_AND_SEARCH_LIST_STEP
        notifyItemChanged(itemCount)
        this.contentList.addAll(contentList)
        notifyItemRangeInserted(mainList.size + 1, contentList.size)
    }

    private fun refreshMainList(list: List<T>) {
        val itemCount = mainList.size
        mainList.clear()
        if (itemCount != 0) notifyItemRangeRemoved(0, itemCount)
        mainList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }

    private fun clearSearchList() {
        val itemCount = super.getItemCount()
        contentList.clear()
        if (itemCount != 0) notifyItemRangeRemoved(getItemCount(), itemCount)
    }

    fun clearAllList() {
        currentState = EMPTY_LIST_STEP
        mainList.clear()
        super.clearList()
    }

    /**
     * mainList список из базы, contentList из сети
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