package com.thebrodyaga.vkurse.common

import android.support.v4.widget.ContentLoadingProgressBar
import android.view.View

/**
 * Created by admin
 *         on 02/10/2018.
 */

fun View.visibleOrInvisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

fun ContentLoadingProgressBar.toggleVisible(isVisible: Boolean) {
    if (isVisible) this.show()
    else hide()
}