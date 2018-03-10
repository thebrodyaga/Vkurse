package com.thebrodyaga.vkurse.mvp.models.gson

import com.thebrodyaga.vkobjects.wall.WallPostFull


/**
 * Created by Emelyanov.N4 on 22.02.2018.
 */

data class VkWallResponse(val wallPostList: List<WallPostFull>,
                          val ownerInfoList: List<OwnerInfo>)

data class OwnerInfo(val ownerId: Int,
                     val offset: Int,
                     val count: Int? = null)

data class VkWallBody(var timeStep: Int,
                      var lastPostDate: Int? = null,
                      var ownerInfoList: List<OwnerInfo>)

val testOwnerInfoList = listOf(OwnerInfo(1, 0), OwnerInfo(-1, 0),
        OwnerInfo(-11, 0), OwnerInfo(-222, 0))