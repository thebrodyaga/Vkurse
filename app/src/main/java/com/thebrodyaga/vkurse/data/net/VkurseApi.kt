package com.thebrodyaga.vkurse.data.net

import com.thebrodyaga.vkobjects.groups.GroupFull
import com.thebrodyaga.vkobjects.groups.responses.SearchResponse
import com.thebrodyaga.vkurse.models.gson.VkWallBody
import com.thebrodyaga.vkurse.models.gson.VkWallResponse
import io.reactivex.Observable
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
    fun getFirstWall(@Body vkWallBody: VkWallBody): Observable<VkWallResponse>

    @POST("vk/wallAfterLast")
    fun getWallAfterLast(@Body vkWallBody: VkWallBody): Observable<VkWallResponse>

    @POST("vk/newWall")
    fun getNewWall(@Body vkWallBody: VkWallBody): Observable<VkWallResponse>

    @GET("vk/searchGroups")
    fun searchGroups(@Query("q") query: String,
                     @Query("offset") offset: Int?): Observable<SearchResponse>

    /**
     * один из параметров обязательный
     */
    @GET("vk/getGroupsById")
    fun getGroupsById(@Query("groupId") groupId: Int?,
                      @Query("groupIds") vararg groupIds: Int?): Observable<List<GroupFull>>

    /**
     * информация об одной группе
     */
    @GET("vk/getGroupsById")
    fun getSingleGroup(@Query("groupId") groupId: Int): Observable<List<GroupFull>>
}
