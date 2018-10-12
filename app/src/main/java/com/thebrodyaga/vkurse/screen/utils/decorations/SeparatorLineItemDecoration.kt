package com.thebrodyaga.vkurse.screen.utils.decorations

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.thebrodyaga.vkurse.domain.entities.ui.groupsList.ItemsForGroupsList

/**
 * Created by admin
 *         on 11/10/2018.
 */
class SeparatorLineItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val viewType = parent.adapter?.getItemViewType(
                parent.getChildAdapterPosition(view)) ?: return
        if (viewType != ItemsForGroupsList.GROUP.viewType) return
        outRect.bottom = 1
//        outRect.
    }
}