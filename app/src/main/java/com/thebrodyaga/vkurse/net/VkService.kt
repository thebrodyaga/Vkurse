package com.thebrodyaga.vkurse.net

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

    companion object {
        const val timeStep = 60 * 60 * 2 //2 часа
    }
}