package com.thebrodyaga.vkurse.ui.adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.thebrodyaga.vkobjects.wall.WallPostFull
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.getDate
import kotlinx.android.synthetic.main.card_item.view.*

/**
 * Created by Emelyanov.N4 on 03.03.2018.
 */
class VkPostsAdapter(private val onLoadMoreListener: OnLoadMoreListener,
                     recyclerView: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    totalItemCount = linearLayoutManager.itemCount
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    if (!loading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                        setProgressItem()
                        onLoadMoreListener.onLoadMore()
                        loading = true
                    }
                }
            })
        }
    }

    private var wallPostList = arrayListOf<WallPostFull?>()
    private val visibleThreshold = 2    //последний видимый перед загрузкой
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var loading: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_POST)
            PostHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false))
        else ProgressHolder(ProgressBar(parent.context))


    }

    override fun getItemCount(): Int {
        return wallPostList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (wallPostList[position] != null) VIEW_POST else VIEW_PROGRESS
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProgressBar -> (holder as ProgressHolder).progressBar.isIndeterminate = true
            is PostHolder -> {
                val wallPostFull = wallPostList[position]
                if (wallPostFull != null) {
                    holder.postTitle.text = (wallPostFull.ownerId.toString() + "  " + wallPostFull.id.toString())
                    holder.postDate.text = getDate(wallPostFull.date.toLong() * 1000)
                }
            }
        }

    }

    fun setPostToEnd(wallPostList: List<WallPostFull>) {
        if (!this.wallPostList.isEmpty()) removedProgressItem()
        this.wallPostList.addAll(wallPostList)
        notifyDataSetChanged()
    }

    fun removedProgressItem() {
        if (!loading) return
        wallPostList.removeAt(wallPostList.size - 1)
        notifyItemRemoved(wallPostList.size)
        loading = false
    }

    private fun setProgressItem() {
        wallPostList.add(null)
        notifyItemInserted(wallPostList.size - 1)
    }

    class PostHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        val postTitle: TextView = itemView.title_text
        val postDate: TextView = itemView.subtitle_text
    }

    class ProgressHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        val progressBar: ProgressBar = containerView as ProgressBar

    }

    companion object {
        const val VIEW_POST = 1
        const val VIEW_PROGRESS = 0
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }
}