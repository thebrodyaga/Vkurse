package com.thebrodyaga.vkurse.screen.adapters

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.getDate
import com.thebrodyaga.vkurse.domain.entities.ui.ItemModel
import com.thebrodyaga.vkurse.domain.entities.ui.postList.ItemsForPostList
import com.thebrodyaga.vkurse.domain.entities.ui.postList.VkPostItem
import com.thebrodyaga.vkurse.screen.base.BaseAdapter
import com.thebrodyaga.vkurse.screen.base.BaseListAdapter
import kotlinx.android.synthetic.main.item_post.view.*

/**
 * Created by Emelyanov.N4
 *         on 03.03.2018.
 */
class VkPostsAdapter : BaseListAdapter<ItemModel<ItemsForPostList>>(VkPostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (viewType) {
                ItemsForPostList.POST.viewType -> PostHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_post, parent, false))
                ItemsForPostList.PROGRESS.viewType -> BaseAdapter.ProgressHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.middle_progress_bar, parent, false))
                else -> throw RuntimeException("Not valid viewType")
            }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostHolder -> holder.bind(getItem(position) as VkPostItem, onListItemClick)
        }
    }

    override fun getItemViewType(position: Int): Int =
            getItem(position).getItemType().viewType


    class PostHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        fun bind(vkPostItem: VkPostItem, onListItemClick: ((item: ItemModel<ItemsForPostList>, position: Int, view: View) -> Unit)?) = with(itemView) {
            setOnClickListener { onListItemClick?.invoke(vkPostItem, adapterPosition, it) }
            val wallPostFull = vkPostItem.vkPost.wallPostFull
            post_title.text = (wallPostFull.ownerId.toString() + "  " + wallPostFull.id.toString())
            post_subtitle.text = getDate(wallPostFull.date.toLong() * 1000)
        }

    }

    private class VkPostDiffCallback : DiffUtil.ItemCallback<ItemModel<ItemsForPostList>>() {
        override fun areItemsTheSame(old: ItemModel<ItemsForPostList>, new: ItemModel<ItemsForPostList>): Boolean {
            val oldType = old.getItemType()
            val newType = new.getItemType()
            return when {
                oldType != newType -> false
                oldType == ItemsForPostList.POST && newType == ItemsForPostList.POST ->
                    (old as VkPostItem).vkPost.wallPostFull.id == (new as VkPostItem).vkPost.wallPostFull.id
                oldType == newType -> true
                else -> false
            }
        }

        override fun areContentsTheSame(old: ItemModel<ItemsForPostList>, new: ItemModel<ItemsForPostList>): Boolean {
            val oldType = old.getItemType()
            val newType = new.getItemType()
            return when {
                oldType != newType -> false
                oldType == ItemsForPostList.POST && newType == ItemsForPostList.POST ->
                    (old as VkPostItem).vkPost.wallPostFull == (new as VkPostItem).vkPost.wallPostFull
                oldType == newType -> true
                else -> false
            }
        }
    }
}