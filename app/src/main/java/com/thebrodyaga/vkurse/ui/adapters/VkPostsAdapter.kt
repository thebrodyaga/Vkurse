package com.thebrodyaga.vkurse.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.thebrodyaga.vkobjects.wall.WallPostFull
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.getDate
import kotlinx.android.synthetic.main.card_item.view.*

/**
 * Created by Emelyanov.N4 on 03.03.2018.
 */
class VkPostsAdapter : RecyclerView.Adapter<VkPostsAdapter.ViewHolder>() {
    private var wallPostList = arrayListOf<WallPostFull>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false))
    }

    override fun getItemCount(): Int {
        return wallPostList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wallPostFull = wallPostList[position]
        holder.postTitle.text = (wallPostFull.ownerId.toString() + "  " + wallPostFull.id.toString())
        holder.postDate.text = getDate(wallPostFull.date.toLong() * 1000)
    }

    fun setPostToEnd(wallPostList: List<WallPostFull>) {
        this.wallPostList.addAll(wallPostList)
        notifyDataSetChanged()
    }

    class ViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        val postTitle: TextView = itemView.title_text
        val postDate: TextView = itemView.subtitle_text
    }
}