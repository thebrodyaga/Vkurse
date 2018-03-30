package com.thebrodyaga.vkurse.ui.adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.squareup.picasso.Picasso
import com.thebrodyaga.vkurse.R
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
            //TODO спамит подгрузку если сервак отвалился, поискать как сделать по-красоте
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy <= 0 || isLoading) return
                if (linearLayoutManager.itemCount
                        == linearLayoutManager.findLastVisibleItemPosition() + visibleThreshold) {
                    isLoading = true
                    contentList.add(null)
                    recyclerView?.post { notifyDataSetChanged() }
                    onLoadMoreListener.onLoadMore()
                }
            }
        })
    }

    @Inject
    protected lateinit var picasso: Picasso
    protected val contentList = arrayListOf<T?>()
    protected val visibleThreshold = 1    //последний видимый перед загрузкой
    protected var isLoading: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProgressHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.middle_progress_bar, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProgressBar -> (holder as ProgressHolder)
        }
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (contentList[position] != null) VIEW_ITEM else VIEW_PROGRESS
    }


    fun setToEnd(contentList: List<T>) {
        if (!this.contentList.isEmpty()) removedProgressItem()
        this.contentList.addAll(contentList)
        notifyDataSetChanged()
    }

    fun setToStart(contentList: List<T>) {
        this.contentList.addAll(0, contentList)
        notifyDataSetChanged()
    }

    fun clearList() {
        contentList.clear()
        isLoading = false
        notifyDataSetChanged()
    }

    fun removedProgressItem() {
        if (!isLoading) return
        contentList.removeAt(contentList.size - 1)
        notifyDataSetChanged()
        isLoading = false
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