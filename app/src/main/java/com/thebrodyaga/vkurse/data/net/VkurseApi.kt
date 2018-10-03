package com.thebrodyaga.vkurse.data.net

import com.thebrodyaga.vkobjects.groups.GroupFull
import com.thebrodyaga.vkobjects.groups.responses.SearchResponse
import com.thebrodyaga.vkurse.domain.entities.gson.VkWallBody
import com.thebrodyaga.vkurse.domain.entities.gson.VkWallResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Emelyanov.N4
 *         on 16.12.2017.
 */

interface VkurseApi {
    @POST("vk/firstWall")
    fun getFirstWall(@Body vkWallBody: VkWallBody): Single<VkWallResponse>

    @POST("vk/wallAfterLast")
    fun getWallAfterLast(@Body vkWallBody: VkWallBody): Single<VkWallResponse>

    @POST("vk/newWall")
    fun getNewWall(@Body vkWallBody: VkWallBody): Single<VkWallResponse>

    @GET("vk/searchGroups")
    fun searchGroups(@Query("q") query: String,
                     @Query("offset") offset: Int?): Single<SearchResponse>

    /**
     * один из параметров обязательный
     */
    @GET("vk/getGroupsById")
    fun getGroupsById(@Query("groupId") groupId: Int?,
                      @Query("groupIds") vararg groupIds: Int?): Single<List<GroupFull>>

    /**
     * информация об одной группе
     */
    @GET("vk/getGroupsById")
    fun getSingleGroup(@Query("groupId") groupId: Int): Observable<List<GroupFull>>
}
