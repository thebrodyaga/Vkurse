package com.thebrodyaga.vkurse.net

import com.thebrodyaga.vkurse.mvp.models.gson.OwnerInfo
import com.thebrodyaga.vkurse.mvp.models.gson.VkWall
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Emelyanov.N4 on 16.12.2017.
 */

interface VkurseApi {
    @POST("wallPostById")
    fun getWall(@Body ownerInfoList: List<OwnerInfo>): Observable<VkWall>

}
