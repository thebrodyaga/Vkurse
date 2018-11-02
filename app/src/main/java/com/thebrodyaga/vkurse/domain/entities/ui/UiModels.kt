package com.thebrodyaga.vkurse.domain.entities.ui

import com.thebrodyaga.vkobjects.groups.GroupFull
import com.thebrodyaga.vkobjects.photos.Photo
import com.thebrodyaga.vkobjects.photos.PhotoAlbum
import com.thebrodyaga.vkobjects.users.UserFull
import com.thebrodyaga.vkobjects.video.Video
import com.thebrodyaga.vkobjects.wall.WallPostFull
import com.thebrodyaga.vkurse.common.gcd
import com.thebrodyaga.vkurse.common.getMaxSize
import com.thebrodyaga.vkurse.domain.entities.room.VkGroup

/**
 * Created by admin
 *         on 23.09.2018.
 */


data class VkPost(val wallPostFull: WallPostFull,
                  val profiles: Map<Int, UserFull>,
                  val groups: Map<Int, GroupFull>)

/**
 * if (fromApiList==null) значит полный список, ничего не ищет
 */
data class VkSearchGroup(var state: SearchGroupState,
                         val localeList: List<VkGroup>,
                         val fromApiList: List<VkGroup>)

enum class SearchGroupState {
    LOCAL_DATA, SEARCH_PROGRESS, SEARCH_RESULT
}

interface ItemModel<ItemsEnum> {
    fun getItemType(): ItemsEnum
}

data class ImageDto(val url: String,
                    val height: Int,
                    val width: Int,
                    val index: Int = 0) {
    private val imageGcd = gcd(height, width)
    val heightRatio = height / imageGcd
    val widthRatio = width / imageGcd

    companion object {
        fun newInstance(photo: Photo): ImageDto =
                ImageDto(photo.getMaxSize(), photo.height, photo.width)

        fun newInstance(photoAlbum: PhotoAlbum): ImageDto = photoAlbum.thumb
                .let { ImageDto(it.getMaxSize(), it.height, it.width) }

        fun newInstance(video: Video): ImageDto {
            var height = 1
            var width = 1
            var url = ""
            video.photo800?.let {
                url = it
                height = 450
                width = 800
            } ?: video.photo320?.let {
                url = it
                height = 240
                width = 320
            } ?: video.photo130?.let {
                url = it
                height = 98
                width = 130
            }
            return ImageDto(url, height, width)
        }
    }
}
