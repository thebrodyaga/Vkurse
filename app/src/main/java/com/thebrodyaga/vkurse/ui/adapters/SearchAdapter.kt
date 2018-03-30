package com.thebrodyaga.vkurse.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.thebrodyaga.vkurse.common.DEBUG_TAG

/**
 * Created by Emelyanov.N4
 *         on 30.03.2018
 */

abstract class SearchAdapter<T>(onLoadMoreListener: OnLoadMoreListener?,
                                recyclerView: RecyclerView) : BaseAdapter<T>(onLoadMoreListener, recyclerView) {
    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.i(DEBUG_TAG, "onCreateViewHolder")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.i(DEBUG_TAG, "onBindViewHolder")
    }*/

    private val contentInDevice = arrayListOf<T>()

    override fun getItemCount(): Int {
        return when (checkStep()) {
            DEVICE_LIST_STEP -> contentInDevice.size
            NEW_SEARCH_STEP -> contentInDevice.size + contentList.size
            DEVICE_AND_SEARCH_LIST_STEP -> contentInDevice.size + 1 + contentList.size
            else -> 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (contentList.isEmpty())
            if (contentList.isEmpty()) return VIEW_ITEM
        return if (contentList[position] != null) VIEW_ITEM else VIEW_PROGRESS
    }

    /**
     * contentInDevice список из базы, contentList из сети
     * DEVICE_LIST_STEP список из базы, NEW_SEARCH_STEP список из базы и прогресс в списке из сети
     * DEVICE_AND_SEARCH_LIST_STEP два списка разделенные заголовком
     */
    private fun checkStep(): String {
        if (contentList.isEmpty()) return DEVICE_LIST_STEP
        if (contentList[0] == null) return NEW_SEARCH_STEP
        return if (!contentList.isEmpty() && !contentInDevice.isEmpty())
            DEVICE_AND_SEARCH_LIST_STEP
        else EMPTY_LIST_STEP
    }

    companion object {
        const val VIEW_HEADER = 2
        const val VIEW_HEADER_PROGRESS = 3
        private const val DEVICE_LIST_STEP = "deviceListStep"
        private const val NEW_SEARCH_STEP = "newSearchStep"
        private const val DEVICE_AND_SEARCH_LIST_STEP = "deviceAndSearchListStep"
        private const val EMPTY_LIST_STEP = "emptyListStep"
    }
}