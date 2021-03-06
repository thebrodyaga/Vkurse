package com.thebrodyaga.vkurse.data.net

import com.thebrodyaga.vkobjects.groups.GroupFull
import com.thebrodyaga.vkobjects.groups.responses.SearchResponse
import com.thebrodyaga.vkurse.domain.entities.gson.VkWallBody
import com.thebrodyaga.vkurse.domain.entities.gson.VkWallResponse
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Singleton

/**
 * Created by Emelyanov.N4
 *         on 21.02.2018.
 */

@Singleton
class VkService(private var vkurseApi: VkurseApi) {

    fun getFirstList(vkWallBody: VkWallBody): Single<VkWallResponse> {
        return vkurseApi.getFirstWall(vkWallBody)
    }

    fun getListAfterLast(vkWallBody: VkWallBody): Single<VkWallResponse> {
        return vkurseApi.getWallAfterLast(vkWallBody)
    }

    fun getNewWall(vkWallBody: VkWallBody): Single<VkWallResponse> {
        return vkurseApi.getNewWall(vkWallBody)
    }

    fun searchGroups(query: String, offset: Int = 0): Single<SearchResponse> {
        return vkurseApi.searchGroups(query, offset)
    }

    fun getGroupsById(groupId: Int?, vararg groupIds: Int?): Single<List<GroupFull>> {
        return vkurseApi.getGroupsById(groupId, *groupIds)
    }

    fun getSingleGroup(groupId: Int): Single<GroupFull>{
        return vkurseApi.getSingleGroup(groupId).firstOrError().flatMap { Single.just(it[0]) }
    }
}