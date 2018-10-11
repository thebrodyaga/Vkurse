package com.thebrodyaga.vkurse.repository.imp

import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkobjects.groups.GroupFull
import com.thebrodyaga.vkurse.common.clearAndAddAll
import com.thebrodyaga.vkurse.common.debugLogging
import com.thebrodyaga.vkurse.data.db.RoomDatabase
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.domain.entities.ui.VkSearchGroup
import com.thebrodyaga.vkurse.domain.entities.room.VkGroup
import com.thebrodyaga.vkurse.domain.entities.ui.SearchGroupState
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.ArrayList
import java.util.concurrent.TimeUnit
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
    private var isHaveNewData = true
    private var searchState = VkSearchGroup(SearchGroupState.LOCAL_DATA, listOf(), listOf())

    val favoriteGroupsCash = mutableListOf<VkGroup>()

    fun newSearchGroups(query: String): Observable<VkSearchGroup> {
        return Observable.create<VkSearchGroup> { emitter ->
            searchState = VkSearchGroup(SearchGroupState.SEARCH_PROGRESS,
                    favoriteGroupsCash.filter { it.name.startsWith(query) }, listOf())
            emitter.onNext(searchState)
            debugLogging("newSearchGroups $query, local result emitter")
            try {
                Thread.sleep(2000)
            } catch (e: Exception) {
            }
            if (emitter.isDisposed) return@create
            vkService.searchGroups(currentQuery, currentOffset)
                    .doAfterSuccess { setCurrentState(query, it.items.size) }
                    .subscribe({
                        searchState = VkSearchGroup(SearchGroupState.SEARCH_RESULT,
                                searchState.localeList, bindVkGroupList(it.items))
                        emitter.onNext(searchState)
                        isHaveNewData = searchState.fromApiList.size < it.count
                        emitter.onComplete()
                    }, { emitter.tryOnError(it) })
            debugLogging("newSearchGroups $query, api result emitter")
        }.doOnSubscribe {
            setCurrentState(query, null)
            isHaveNewData = true
        }
    }

    fun offsetSearchGroups(): Single<VkSearchGroup> {
        return if (isHaveNewData)
            vkService.searchGroups(currentQuery, currentOffset)
                    .doAfterSuccess { setCurrentState(currentQuery, it.items.size) }
                    .flatMap {
                        val newFromApiList = mutableListOf<VkGroup>().apply {
                            addAll(searchState.fromApiList)
                            addAll(bindVkGroupList(it.items))
                        }
                        searchState = VkSearchGroup(SearchGroupState.SEARCH_RESULT,
                                searchState.localeList, newFromApiList)
                        isHaveNewData = searchState.fromApiList.size < it.count
                        Single.just(searchState)
                    } else Single.just(searchState)
    }

    fun getFavoriteGroups(): Flowable<List<VkGroup>> =
            roomDatabase.getGroupDao().getFlowableGroups()
                    .doOnNext { favoriteGroupsCash.clearAndAddAll(it) }

    fun addFavoriteGroup(vkGroup: VkGroup) =
            roomDatabase.getGroupDao().insertGroups(vkGroup)

    fun loadFullGroup(currentGroup: Int): Single<GroupFull> =
            vkService.getSingleGroup(currentGroup)

    private fun setCurrentState(query: String, offset: Int?) {
        synchronized(this) {
            currentQuery = query
            if (offset != null)
                currentOffset += offset
            else currentOffset = 0
        }
    }

    private fun bindVkGroupList(list: List<Group>): MutableList<VkGroup> {
        val result = mutableListOf<VkGroup>()
        list.forEach { result.add(VkGroup.newInstance(it)) }
        return result
    }
}