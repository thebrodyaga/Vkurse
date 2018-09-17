package com.thebrodyaga.vkurse.screen.fragments.postList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.thebrodyaga.vkobjects.wall.WallPostFull
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.getDate
import com.thebrodyaga.vkurse.screen.base.BaseAdapter
import kotlinx.android.synthetic.main.post_item_card.view.*

/**
 * Created by Emelyanov.N4
 *         on 03.03.2018.
 */
class VkPostsAdapter(onLoadMoreListener: OnLoadMoreListener?) : BaseAdapter<WallPostFull>(onLoadMoreListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_ITEM -> {
                PostHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.post_item_card, parent, false))
            }
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostHolder -> {
                val wallPostFull = contentList[position] ?: return
                holder.postTitle.text = (wallPostFull.ownerId.toString() + "  " + wallPostFull.id.toString())
                holder.postDate.text = getDate(wallPostFull.date.toLong() * 1000)
            }
        }
    }

    class PostHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        val postTitle: TextView = itemView.title_text
        val postDate: TextView = itemView.subtitle_text
    }
}