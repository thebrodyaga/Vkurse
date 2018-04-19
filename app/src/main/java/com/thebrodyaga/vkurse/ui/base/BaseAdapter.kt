package com.thebrodyaga.vkurse.ui.base

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.application.App

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */


abstract class BaseAdapter<T>(private val onLoadMoreListener: OnLoadMoreListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var picasso: Picasso = App.appComponent.getPicasso()
    protected val contentList = arrayListOf<T?>()
    protected val visibleThreshold = 1    //последний видимый перед загрузкой
    private var isLoading: Boolean = false
    private var recyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProgressHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.middle_progress_bar, parent, false))
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (contentList[position] != null) VIEW_ITEM else VIEW_PROGRESS
    }


    fun setToEnd(contentList: List<T>) {
        val startPosition = itemCount
        this.contentList.addAll(contentList)
        //сдвиг positionStart на 1, тк progressItem удаляется и view обновляется
        notifyItemRangeInserted(startPosition, contentList.size)
    }

    fun setToStart(contentList: List<T>) {
        this.contentList.addAll(0, contentList)
        notifyItemRangeInserted(0, contentList.size)
    }

    fun clearList() {
        contentList.clear()
        isLoading = false
        notifyDataSetChanged()
    }

    fun insertProgressItem() {
        if (isLoading) return
        contentList.add(null)
        notifyItemInserted(itemCount - 1)   // прилетает из onScrolled -> presenter
        isLoading = true
    }

    fun removedProgressItem() {
        if (!isLoading || contentList.isEmpty()) return
        contentList.removeAt(contentList.size - 1)
        notifyItemRemoved(itemCount)
        isLoading = false
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        if (onLoadMoreListener == null) return
        val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return
        this.recyclerView = recyclerView
        (this.recyclerView)?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            //TODO спамит подгрузку если сервак отвалился, поискать как сделать по-красоте
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy <= 0 || isLoading || contentList.isEmpty()) return
                if (linearLayoutManager.itemCount
                        == linearLayoutManager.findLastVisibleItemPosition() + visibleThreshold)
                    recyclerView?.post { onLoadMoreListener.onLoadMore() }
            }
        })
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = null
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