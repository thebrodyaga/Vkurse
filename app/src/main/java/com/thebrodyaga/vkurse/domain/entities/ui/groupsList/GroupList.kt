package com.thebrodyaga.vkurse.domain.entities.ui.groupsList

import com.thebrodyaga.vkurse.domain.entities.room.VkGroup
import com.thebrodyaga.vkurse.domain.entities.ui.ItemModel

/**
 * Created by admin
 *         on 09/10/2018.
 */

data class VkGroupItem(val vkGroup: VkGroup) : ItemModel<ItemsForGroupsList> {
    override fun getItemType(): ItemsForGroupsList = ItemsForGroupsList.GROUP
}

data class HeaderItem(val text: String = "notNecessarily") : ItemModel<ItemsForGroupsList> {
    override fun getItemType(): ItemsForGroupsList = ItemsForGroupsList.HEADER
}

data class ProgressItem(private val text: String = "notNecessarily") : ItemModel<ItemsForGroupsList> {
    override fun getItemType(): ItemsForGroupsList = ItemsForGroupsList.PROGRESS
}

data class ProgressHeaderItem(private val text: String = "notNecessarily") : ItemModel<ItemsForGroupsList> {
    override fun getItemType(): ItemsForGroupsList = ItemsForGroupsList.PROGRESS_HEADER
}

enum class ItemsForGroupsList(val viewType: Int) {
    GROUP(0), HEADER(1), PROGRESS(2), PROGRESS_HEADER(3)
}