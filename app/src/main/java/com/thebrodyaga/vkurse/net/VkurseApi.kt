package com.thebrodyaga.vkurse.net

import com.thebrodyaga.vkurse.mvp.models.gson.VkWallBody
import com.thebrodyaga.vkurse.mvp.models.gson.VkWallResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Emelyanov.N4 on 16.12.2017.
 */

interface VkurseApi {
    @POST("vk/firstWall")
    fun getWall(@Body vkWallBody: VkWallBody): Observable<VkWallResponse>

}
