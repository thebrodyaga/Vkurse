package com.thebrodyaga.vkurse.screen.groupList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.screen.base.SearchAdapter
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.group_item.view.*

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */
class VkGroupsAdapter(onLoadMoreListener: OnLoadMoreListener?,
                      private val onItemClickListener: OnItemClickListener? = null)
    : SearchAdapter<Group>(onLoadMoreListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_ITEM -> {
                GroupHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.group_item, parent, false),
                        onItemClickListener)
            }
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GroupHolder -> {
                val group =
                        if (holder.adapterPosition in 0 until mainList.size) mainList[holder.adapterPosition]
                        else contentList[holder.adapterPosition - mainList.size - 1] ?: return
                holder.groupName.text = group.name ?: holder.adapterPosition.toString()
                picasso.load(group.photo50).into(holder.groupIcon)
            }
        }
    }

    class GroupHolder(containerView: View,
                      onItemClickListener: OnItemClickListener?)
        : RecyclerView.ViewHolder(containerView) {
        val groupName: TextView = itemView.singleLineText
        val groupIcon: CircleImageView = itemView.icon

        init {
            itemView.setOnClickListener { onItemClickListener?.onListItemClick(it, adapterPosition) }
        }
    }
}