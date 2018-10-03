package com.thebrodyaga.vkurse.domain.entities

import com.thebrodyaga.vkobjects.groups.GroupFull
import com.thebrodyaga.vkobjects.users.UserFull
import com.thebrodyaga.vkobjects.wall.WallPostFull

/**
 * Created by admin
 *         on 23.09.2018.
 */


data class VkPost(val wallPostFull: WallPostFull,
                  val profiles: Map<Int, UserFull>,
                  val groups: Map<Int, GroupFull>)