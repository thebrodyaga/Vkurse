package com.thebrodyaga.vkurse.repository

import com.thebrodyaga.vkobjects.groups.GroupFull
import com.thebrodyaga.vkobjects.groups.responses.SearchResponse
import com.thebrodyaga.vkurse.data.db.RoomDatabase
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.domain.entities.room.VkGroup
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Singleton

/**
 * Created by Win10
 *         on 09.05.2018.
 */

@Singleton
class GroupRepository(vkService: VkService,
                      roomDatabase: RoomDatabase) : BaseRepository(vkService, roomDatabase) {

    private var currentOffset = 0
    private var currentQuery = ""

    fun newSearchGroups(query: String): Single<SearchResponse> {
        return vkService.searchGroups(query)
    }

    fun offsetSearchGroups(): Single<SearchResponse> {
        return vkService.searchGroups(currentQuery, currentOffset)
    }

    fun setCurrentState(query: String? = null, offset: Int?) {
        synchronized(this) {
            if (query != null) currentQuery = query
            if (offset != null) currentOffset += offset
            else currentOffset = 0
        }
    }

    fun getFavoriteGroups(): Flowable<List<VkGroup>> =
            roomDatabase.getGroupDao().getFlowableGroups()


    fun addFavoriteGroup(vkGroup: VkGroup) =
            roomDatabase.getGroupDao().insertGroups(vkGroup)

    fun loadFullGroup(currentGroup: Int): Single<GroupFull> =
            vkService.getSingleGroup(currentGroup)
}