package com.thebrodyaga.vkurse.screen.fragments.main.di

import com.thebrodyaga.vkurse.application.di.scopes.ActivityScope
import com.thebrodyaga.vkurse.repository.PostRepository
import com.thebrodyaga.vkurse.repository.imp.GroupRepository
import com.thebrodyaga.vkurse.screen.fragments.main.mvp.MainInteractor
import dagger.Module
import dagger.Provides

/**
 * Created by Win10
 *         on 16.04.2018.
 */
@Module
class MainModule {

    @ActivityScope
    @Provides
    fun provideMainInteractor(groupRepository: GroupRepository)
            : MainInteractor = MainInteractor(groupRepository)
}