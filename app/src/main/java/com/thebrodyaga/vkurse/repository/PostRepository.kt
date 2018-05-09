package com.thebrodyaga.vkurse.repository

import com.thebrodyaga.vkurse.common.timeStep
import com.thebrodyaga.vkurse.data.db.RoomDatabase
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.models.gson.OwnerInfo
import com.thebrodyaga.vkurse.models.gson.VkWallBody
import com.thebrodyaga.vkurse.models.gson.VkWallResponse
import com.thebrodyaga.vkurse.models.room.VkGroup
import io.reactivex.Observable
import java.util.ArrayList
import javax.inject.Singleton

/**
 * Created by Win10
 *         on 09.05.2018.
 */

@Singleton
class PostRepository(vkService: VkService,
                     roomDatabase: RoomDatabase) : BaseRepository(vkService, roomDatabase) {

    private val currentState = VkWallBody(timeStep = timeStep, ownerInfoList = listOf())

    fun loadFirstWall(): Observable<VkWallResponse> {
        return vkService.getFirstList(currentState)
    }

    fun loadAfterLast(): Observable<VkWallResponse> {
        return vkService.getListAfterLast(currentState)
    }

    fun loadNewWall(): Observable<VkWallResponse> {
        return vkService.getNewWall(currentState)
    }

    fun newCurrentState(groupsList: List<VkGroup>) {
        synchronized(this) {
            val ownerInfoList = ArrayList<OwnerInfo>()
            groupsList.forEach { ownerInfoList.add(OwnerInfo(-1 * it.id)) }
            currentState.ownerInfoList = ownerInfoList
        }
    }

    fun setCurrentState(it: VkWallResponse, last: Int? = null, first: Int? = null) {
        synchronized(this) {
            if (last != null) currentState.lastPostDate = last
            if (first != null) currentState.firstPostDate = first
            currentState.ownerInfoList = it.ownerInfoList
        }
    }
}