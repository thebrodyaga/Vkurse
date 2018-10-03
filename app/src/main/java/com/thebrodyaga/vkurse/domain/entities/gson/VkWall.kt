package com.thebrodyaga.vkurse.domain.entities.gson

import com.thebrodyaga.vkobjects.groups.GroupFull
import com.thebrodyaga.vkobjects.users.UserFull
import com.thebrodyaga.vkobjects.wall.WallPostFull


/**
 * Created by Emelyanov.N4
 *         on 22.02.2018.
 */

data class VkWallResponse(val wallPostList: List<WallPostFull>,
                          val ownerInfoList: List<OwnerInfo>,
                          val profiles: Set<UserFull>,
                          val groups: Set<GroupFull>)

data class OwnerInfo(val ownerId: Int,
                     val offset: Int = 0,
                     val count: Int? = null)

data class VkWallBody(var timeStep: Int,
                      var lastPostDate: Int? = null,
                      var firstPostDate: Int? = null,
                      var ownerInfoList: List<OwnerInfo>)

val testOwnerInfoList = listOf(OwnerInfo(1, 0), OwnerInfo(-1, 0),
        OwnerInfo(-11, 0), OwnerInfo(-222, 0))