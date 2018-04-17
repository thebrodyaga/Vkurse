package com.thebrodyaga.vkurse.ui.list.groups

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.ui.base.SearchAdapter
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_group.view.*

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */
class VkGroupsAdapter(onLoadMoreListener: OnLoadMoreListener?) : SearchAdapter<Group>(onLoadMoreListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_ITEM -> {
                GroupHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_group, parent, false))
            }
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GroupHolder -> {
                val group =
                        if (position in 0 until mainList.size) mainList[position]
                        else contentList[position - mainList.size - 1]
                                ?: return
                holder.groupName.text = group.name ?: position.toString()
                picasso.load(group.photo50).into(holder.groupIcon)
            }
        }
    }

    class GroupHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        val groupName: TextView = itemView.singleLineText
        val groupIcon: CircleImageView = itemView.icon
    }
}