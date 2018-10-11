package com.thebrodyaga.vkurse.screen.adapters

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.application.App
import com.thebrodyaga.vkurse.domain.entities.ui.groupsList.ItemsForGroupsList
import com.thebrodyaga.vkurse.domain.entities.ui.ItemModel
import com.thebrodyaga.vkurse.domain.entities.ui.groupsList.HeaderItem
import com.thebrodyaga.vkurse.domain.entities.ui.groupsList.VkGroupItem
import com.thebrodyaga.vkurse.screen.base.BaseAdapter
import com.thebrodyaga.vkurse.screen.base.BaseListAdapter
import com.thebrodyaga.vkurse.screen.base.SearchAdapter
import kotlinx.android.synthetic.main.item_group.view.*

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */
class VkGroupsAdapter : BaseListAdapter<ItemModel<ItemsForGroupsList>>(DuffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemsForGroupsList.GROUP.viewType -> GroupHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_group, parent, false))
            ItemsForGroupsList.HEADER.viewType -> SearchAdapter.HeaderHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.sub_headers_text, parent, false))
            ItemsForGroupsList.PROGRESS.viewType -> BaseAdapter.ProgressHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.middle_progress_bar, parent, false))
            ItemsForGroupsList.PROGRESS_HEADER.viewType -> {
                val progressBar = LayoutInflater.from(parent.context)
                        .inflate(R.layout.middle_progress_bar, parent, false) as FrameLayout
                progressBar.setPadding(0, 12, 0, 0)
                SearchAdapter.HeaderProgressHolder(progressBar)
            }
            else -> throw RuntimeException("Not valid viewType")
        }
    }

    override fun getItemViewType(position: Int): Int =
            getItem(position).getItemType().viewType

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GroupHolder -> holder.bind(getItem(position) as VkGroupItem, onListItemClick)
        }
    }

    class GroupHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        fun bind(vkGroupItem: VkGroupItem,
                 onListItemClick: ((item: ItemModel<ItemsForGroupsList>, position: Int, view: View) -> Unit)?) = with(itemView) {
            val group = vkGroupItem.vkGroup
            singleLineText.text = group.name
            App.appComponent.getPicasso().load(group.photo50).into(icon)
            setOnClickListener { onListItemClick?.invoke(vkGroupItem, adapterPosition, this) }
        }
    }

    class DuffCallback : DiffUtil.ItemCallback<ItemModel<ItemsForGroupsList>>() {
        override fun areItemsTheSame(old: ItemModel<ItemsForGroupsList>, new: ItemModel<ItemsForGroupsList>): Boolean {
            val oldType = old.getItemType()
            val newType = new.getItemType()
            return when {
                oldType != newType -> false
                oldType == ItemsForGroupsList.GROUP && newType == ItemsForGroupsList.GROUP ->
                    (old as VkGroupItem).vkGroup.id == (new as VkGroupItem).vkGroup.id
                oldType == newType -> true
                else -> false
            }
        }

        override fun areContentsTheSame(old: ItemModel<ItemsForGroupsList>, new: ItemModel<ItemsForGroupsList>): Boolean {
            val oldType = old.getItemType()
            val newType = new.getItemType()
            return when {
                oldType != newType -> false
                oldType == ItemsForGroupsList.GROUP && newType == ItemsForGroupsList.GROUP ->
                    (old as VkGroupItem).vkGroup == (new as VkGroupItem).vkGroup
                oldType == ItemsForGroupsList.HEADER && newType == ItemsForGroupsList.HEADER ->
                    (old as HeaderItem).text == (new as HeaderItem).text
                oldType == ItemsForGroupsList.PROGRESS && newType == ItemsForGroupsList.PROGRESS -> true
                oldType == ItemsForGroupsList.PROGRESS_HEADER && newType == ItemsForGroupsList.PROGRESS_HEADER -> true
                else -> false
            }
        }
    }
}