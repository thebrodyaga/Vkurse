package com.thebrodyaga.vkurse.screen.base

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.Picasso
import com.thebrodyaga.vkurse.application.App

/**
 * Created by admin
 *         on 02/10/2018.
 */
abstract class BaseListAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>)
    : ListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    protected var onListItemClick: ((item: T, position: Int, view: View) -> Unit)? = null

    fun setOnListItemClickListener(onListItemClickListener: ((item: T, position: Int, view: View) -> Unit)?) {
        this.onListItemClick = onListItemClickListener
    }

}