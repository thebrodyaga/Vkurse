package com.thebrodyaga.vkurse.screen.main.di

import com.thebrodyaga.vkurse.application.di.scopes.ActivityScope
import com.thebrodyaga.vkurse.data.db.RoomDatabase
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.screen.main.mvp.MainInteractor
import com.thebrodyaga.vkurse.screen.main.mvp.MainPresenter
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
    fun provideMainInteractor(vkService: VkService, roomDatabase: RoomDatabase)
            : MainInteractor = MainInteractor(vkService, roomDatabase)
}