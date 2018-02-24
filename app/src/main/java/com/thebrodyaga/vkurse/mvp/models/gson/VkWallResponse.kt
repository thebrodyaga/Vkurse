package com.thebrodyaga.vkurse.mvp.models.gson

import com.thebrodyaga.vkobjects.wall.WallPostFull


/**
 * Created by Emelyanov.N4 on 22.02.2018.
 */

data class VkWall(val wallPostList: List<WallPostFull>,
                  val ownerInfoList: List<OwnerInfo>)

data class OwnerInfo(val ownerId: Int,
                     val offset: Int,
                     val count: Int)