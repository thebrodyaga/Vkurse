package com.example.win10.vkurse.app

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
* Created by Emelyanov.N4 on 16.12.2017.
*/

interface VkurseApi {
    @GET("wall.get")
    fun getWall(@Query("owner_id") owner_id: String,
                @Query("access_token") access_token: String,
                @Query("v") v: String): Call<ResponseBody>
}
