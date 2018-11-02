package com.thebrodyaga.vkurse.common

import android.support.v4.widget.ContentLoadingProgressBar
import android.view.View
import com.thebrodyaga.vkobjects.photos.Photo
import com.thebrodyaga.vkobjects.video.Video

/**
 * Created by admin
 *         on 02/10/2018.
 */

fun ContentLoadingProgressBar.toggleVisible(isVisible: Boolean) {
    if (isVisible) this.show()
    else hide()
}

fun <E> MutableCollection<E>.clearAndAddAll(replace: Collection<E>) {
    clear()
    addAll(replace)
}

/**
 * чтоб [IllegalArgumentException] не падал при Enum.valueOf()
 */
inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String): T? {
    return enumValues<T>().find { it.name == name }
}

fun Photo.getMaxSize(): String =
        photo2560 ?: photo1280 ?: photo807 ?: photo604 ?: photo130 ?: photo75 ?: ""

fun Video.getMaxSize(): String =
        photo800 ?: photo320 ?: photo130 ?: ""
