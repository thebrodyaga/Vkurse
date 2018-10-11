package com.thebrodyaga.vkurse.screen.group.di

import com.thebrodyaga.vkurse.application.di.scopes.ActivityScope
import com.thebrodyaga.vkurse.repository.imp.GroupRepository
import com.thebrodyaga.vkurse.screen.group.mvp.VkGroupInteractor
import com.thebrodyaga.vkurse.screen.group.mvp.VkGroupPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by admin
 *         on 05.07.2018.
 */

@Module
class VkGroupActivityModule {

    @Provides
    @ActivityScope
    fun provideInteractor(groupRepository: GroupRepository) = VkGroupInteractor(groupRepository)

    @Provides
    @ActivityScope
    fun providePresenter(vkGroupInteractor: VkGroupInteractor) = VkGroupPresenter(vkGroupInteractor)

}