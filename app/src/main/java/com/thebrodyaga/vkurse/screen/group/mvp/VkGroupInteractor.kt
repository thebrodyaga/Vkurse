package com.thebrodyaga.vkurse.screen.group.mvp

import com.thebrodyaga.vkobjects.groups.GroupFull
import com.thebrodyaga.vkurse.application.di.scopes.ActivityScope
import com.thebrodyaga.vkurse.repository.GroupRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by admin
 *         on 05.07.2018.
 */

@ActivityScope
class VkGroupInteractor @Inject constructor(private val groupRepository: GroupRepository) {
    fun loadFullGroup(currentGroup: Int): Single<GroupFull> {
        return groupRepository.loadFullGroup(currentGroup)
    }
}