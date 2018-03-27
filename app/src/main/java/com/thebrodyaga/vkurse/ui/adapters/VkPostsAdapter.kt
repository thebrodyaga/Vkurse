package com.thebrodyaga.vkurse.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.thebrodyaga.vkobjects.wall.WallPostFull
import com.thebrodyaga.vkurse.App
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.getDate
import kotlinx.android.synthetic.main.card_item.view.*

/**
 * Created by Emelyanov.N4
 *         on 03.03.2018.
 */
class VkPostsAdapter(onLoadMoreListener: OnLoadMoreListener?,
                     recyclerView: RecyclerView) : BaseAdapter<WallPostFull>(onLoadMoreListener, recyclerView) {
    init {
        App.appComponent.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_ITEM)
            PostHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false))
        else ProgressHolder(LayoutInflater.from(parent.context).inflate(R.layout.middle_progress_bar, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProgressBar -> (holder as ProgressHolder)
            is PostHolder -> {
                val wallPostFull = contentList[position] ?: return
                holder.postTitle.text = (wallPostFull.ownerId.toString() + "  " + wallPostFull.id.toString())
                holder.postDate.text = getDate(wallPostFull.date.toLong() * 1000)
            }
        }
    }

    fun setPostToEnd(contentList: List<WallPostFull>) {
        if (!this.contentList.isEmpty()) removedProgressItem()
        this.contentList.addAll(contentList)
        notifyDataSetChanged()
    }

    fun setPostToStart(contentList: List<WallPostFull>) {
        this.contentList.addAll(0, contentList)
        notifyDataSetChanged()
    }

    fun removedProgressItem() {
        if (!isLoading) return
        contentList.removeAt(contentList.size - 1)
        notifyItemRemoved(contentList.size)
        isLoading = false
    }

    class PostHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        val postTitle: TextView = itemView.title_text
        val postDate: TextView = itemView.subtitle_text
    }
}