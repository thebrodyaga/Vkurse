package com.thebrodyaga.vkurse.repository.imp

import com.thebrodyaga.vkobjects.groups.GroupFull
import com.thebrodyaga.vkobjects.users.UserFull
import com.thebrodyaga.vkurse.application.di.scopes.SecondFragment
import com.thebrodyaga.vkurse.common.debugLogging
import com.thebrodyaga.vkurse.common.timeStep
import com.thebrodyaga.vkurse.data.db.RoomDatabase
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.domain.entities.ui.VkPost
import com.thebrodyaga.vkurse.domain.entities.gson.OwnerInfo
import com.thebrodyaga.vkurse.domain.entities.gson.VkWallBody
import com.thebrodyaga.vkurse.domain.entities.gson.VkWallResponse
import com.thebrodyaga.vkurse.domain.entities.gson.testOwnerInfoList
import com.thebrodyaga.vkurse.domain.entities.room.VkGroup
import com.thebrodyaga.vkurse.repository.PostRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import javax.inject.Singleton

/**
 * Created by Win10
 *         on 09.05.2018.
 */

@SecondFragment
class PostRepositoryImp(vkService: VkService,
                        roomDatabase: RoomDatabase)
    : BaseRepository(vkService, roomDatabase), PostRepository {
    init {
        debugLogging("PostRepositoryImp init")
    }

    private var currentState = VkWallBody(timeStep = timeStep, ownerInfoList = testOwnerInfoList/*listOf()*/)
    private val groupsCash = mutableMapOf<Int, GroupFull>()
    private val profilesCash = mutableMapOf<Int, UserFull>()
    private val postsList = mutableListOf<VkPost>()

    override fun loadFirstWall(): Single<List<VkPost>> {
        return vkService.getFirstList(currentState)
                .observeOn(Schedulers.computation())
                .doOnSuccess {
                    updateCurrentState(it, first = it.wallPostList.firstOrNull()?.date,
                            last = it.wallPostList.lastOrNull()?.date)
                }.flatMap {
                    val vkPosts = processedPostResult(it)
                    postsList.clear()
                    postsList.addAll(vkPosts)
                    Single.just(postsList)
                }
    }

    override fun loadAfterLast(): Single<List<VkPost>> =
            vkService.getListAfterLast(currentState)
                    .observeOn(Schedulers.computation())
                    .doAfterSuccess { updateCurrentState(it, last = it.wallPostList.lastOrNull()?.date) }
                    .flatMap {
                        val vkPosts = processedPostResult(it)
                        postsList.addAll(vkPosts)
                        Single.just(postsList)
                    }


    override fun loadNewWall(): Single<List<VkPost>> {
        return vkService.getNewWall(currentState)
                .observeOn(Schedulers.computation())
                .doOnSuccess { updateCurrentState(it, first = it.wallPostList.firstOrNull()?.date) }
                .flatMap {
                    val vkPosts = processedPostResult(it)
                    postsList.addAll(0, vkPosts)
                    Single.just(postsList)
                }
    }

    override fun resetCurrentState(groupsList: List<VkGroup>) {
        synchronized(this) {
            val ownerInfoList = ArrayList<OwnerInfo>()
            groupsList.forEach { ownerInfoList.add(OwnerInfo(-1 * it.id)) }
            currentState.ownerInfoList = ownerInfoList
        }
    }

    private fun updateCurrentState(it: VkWallResponse, last: Int? = null, first: Int? = null) {
        synchronized(this) {
            if (last != null) currentState.lastPostDate = last
            if (first != null) currentState.firstPostDate = first
            currentState.ownerInfoList = it.ownerInfoList
        }
    }

    /**
     * кэшит юзеров и группы, мапит список постов с сылкой на кэш со ВСЕМИ авторами
     */
    private fun processedPostResult(vkWallResponse: VkWallResponse): List<VkPost> {
        vkWallResponse.profiles.forEach { profilesCash[it.id] = it }
        vkWallResponse.groups.forEach { groupsCash[it.id] = it }
        val result = mutableListOf<VkPost>()
        vkWallResponse.wallPostList.forEach { result.add(VkPost(it, profilesCash, groupsCash)) }
        return result
    }
}