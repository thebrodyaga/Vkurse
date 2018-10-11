package com.thebrodyaga.vkurse.domain.entities.ui.postList

import com.thebrodyaga.vkurse.domain.entities.ui.ItemModel
import com.thebrodyaga.vkurse.domain.entities.ui.VkPost

/**
 * Created by admin
 *         on 10/10/2018.
 */

data class VkPostItem(val vkPost: VkPost) : ItemModel<ItemsForPostList> {
    override fun getItemType(): ItemsForPostList = ItemsForPostList.POST
}

data class ProgressItem(private val text: String = "notNecessarily") : ItemModel<ItemsForPostList> {
    override fun getItemType(): ItemsForPostList = ItemsForPostList.PROGRESS
}

enum class ItemsForPostList(val viewType: Int) {
    POST(0), PROGRESS(1)
}