package com.thebrodyaga.vkurse.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkurse.App
import com.thebrodyaga.vkurse.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_group.view.*

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */
class VkGroupsAdapter(onLoadMoreListener: OnLoadMoreListener?,
                      recyclerView: RecyclerView) : BaseAdapter<Group>(onLoadMoreListener, recyclerView) {
    init {
        App.appComponent.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_ITEM)
            GroupHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false))
        else ProgressHolder(LayoutInflater.from(parent.context).inflate(R.layout.middle_progress_bar, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProgressBar -> (holder as ProgressHolder)
            is GroupHolder -> {
                val group = contentList[position] ?: return
                holder.groupName.text = group.name
                picasso.load(group.photo50).into(holder.groupIcon)
            }
        }
    }

    class GroupHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        val groupName: TextView = itemView.singleLineText
        val groupIcon: CircleImageView = itemView.icon
    }
}