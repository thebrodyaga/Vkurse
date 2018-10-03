package com.thebrodyaga.vkurse.screen.fragments.postList

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.getDate
import com.thebrodyaga.vkurse.domain.entities.VkPost
import com.thebrodyaga.vkurse.screen.base.BaseListAdapter
import kotlinx.android.synthetic.main.post_item_card.view.*

/**
 * Created by Emelyanov.N4
 *         on 03.03.2018.
 */
class VkPostsAdapter : BaseListAdapter<VkPost>(VkPostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            PostHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.post_item_card, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostHolder -> holder.bind(getItem(position), onListItemClick)
        }
    }

    class PostHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        fun bind(vkPost: VkPost, onListItemClick: ((item: VkPost, position: Int, view: View) -> Unit)?) = with(itemView) {
            setOnClickListener { onListItemClick?.invoke(vkPost, adapterPosition, it) }
            val wallPostFull = vkPost.wallPostFull
            post_title.text = (wallPostFull.ownerId.toString() + "  " + wallPostFull.id.toString())
            post_subtitle.text = getDate(wallPostFull.date.toLong() * 1000)
        }

    }

    private class VkPostDiffCallback : DiffUtil.ItemCallback<VkPost>() {
        override fun areItemsTheSame(p0: VkPost, p1: VkPost): Boolean {
            return p0.wallPostFull.id == p1.wallPostFull.id
        }

        override fun areContentsTheSame(p0: VkPost, p1: VkPost): Boolean {
            return p0.wallPostFull == p1.wallPostFull
        }
    }
}