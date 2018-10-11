package com.thebrodyaga.vkurse.repository

import com.thebrodyaga.vkurse.domain.entities.ui.VkPost
import com.thebrodyaga.vkurse.domain.entities.room.VkGroup
import io.reactivex.Single

/**
 * Created by admin
 *         on 03/10/2018.
 */
interface PostRepository {
    fun loadFirstWall(): Single<List<VkPost>>

    fun loadAfterLast(): Single<List<VkPost>>

    fun loadNewWall(): Single<List<VkPost>>

    fun resetCurrentState(groupsList: List<VkGroup>)
}