package com.thebrodyaga.vkurse.screen.utils

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.thebrodyaga.vkurse.R

/**
 * Created by admin
 *         on 03/10/2018.
 */
class VerticalOffsetItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: return
        val offsetTop = view.context.resources.getDimensionPixelOffset(R.dimen.item_list_offset_padding_top)
        val offsetBottom = view.context.resources.getDimensionPixelOffset(R.dimen.item_list_offset_padding_bottom)
        outRect.bottom = offsetBottom
        if (position in 1 until itemCount)
            outRect.top = offsetTop
    }
}