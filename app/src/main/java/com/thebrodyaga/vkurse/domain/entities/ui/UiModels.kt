package com.thebrodyaga.vkurse.domain.entities.ui

import com.thebrodyaga.vkobjects.groups.GroupFull
import com.thebrodyaga.vkobjects.users.UserFull
import com.thebrodyaga.vkobjects.wall.WallPostFull
import com.thebrodyaga.vkurse.domain.entities.room.VkGroup
import kotlin.reflect.KClass

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
