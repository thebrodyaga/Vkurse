package com.thebrodyaga.vkurse.screen.utils

import android.view.View
import android.widget.ImageView
import at.blogc.android.views.ExpandableTextView
import com.squareup.picasso.Picasso
import com.thebrodyaga.vkurse.application.App

/**
 * Created by admin
 *         on 11/10/2018.
 */

fun Int.countToText(): String =
        when (this) {
            in 0..999 -> this.toString()
            in 1_000..999_999 -> "${this / 1_000}K"
            in 1_000_000..999_999_999 -> "${this / 1_000_000}M"
            in 1_000_000_000..999_999_999_999 -> "${this / 1_000_000_000}T"
            else -> "${this / 1_000_000_000}T"
        }

fun ExpandableTextView.toggleShowMore(showMoreView: View) {
    if (!isExpanded)
        showMoreView.visibility = View.VISIBLE
    else showMoreView.visibility = View.GONE
    showMoreView.setOnClickListener {
        expand()
        it.visibility = View.GONE
    }
}

fun View.visibleOrInvisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

fun View.visibleOrGone(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Picasso.loadInGrid(url: String, imageView: ImageView) {
    this.load(url)
//            .fit()
//            .centerCrop()
            .into(imageView)
}

fun ImageView.loadImage(url: String) {
    App.appComponent.getPicasso().loadInGrid(url, this)
}