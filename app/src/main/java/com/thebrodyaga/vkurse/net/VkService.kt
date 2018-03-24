package com.thebrodyaga.vkurse.net

import com.thebrodyaga.vkobjects.groups.GroupFull
import com.thebrodyaga.vkobjects.groups.responses.SearchResponse
import com.thebrodyaga.vkurse.mvp.models.gson.VkWallBody
import com.thebrodyaga.vkurse.mvp.models.gson.VkWallResponse
import io.reactivex.Observable

/**
 * Created by Emelyanov.N4 on 21.02.2018.
 */
class VkService(private var vkurseApi: VkurseApi) {

    fun getFirstList(vkWallBody: VkWallBody): Observable<VkWallResponse> {
        return vkurseApi.getFirstWall(vkWallBody)
    }

    fun getListAfterLast(vkWallBody: VkWallBody): Observable<VkWallResponse> {
        return vkurseApi.getWallAfterLast(vkWallBody)
    }

    fun getNewWall(vkWallBody: VkWallBody): Observable<VkWallResponse> {
        return vkurseApi.getNewWall(vkWallBody)
    }

    fun searchGroups(query: String, offset: Int?): Observable<SearchResponse> {
        return vkurseApi.searchGroups(query, offset)
    }

    fun getGroupsById(groupId: Int?, vararg groupIds: Int?): Observable<List<GroupFull>> {
        return vkurseApi.getGroupsById(groupId, *groupIds)
    }

    companion object {
        const val timeStep = 60 * 60 * 2 //2 часа
    }
}