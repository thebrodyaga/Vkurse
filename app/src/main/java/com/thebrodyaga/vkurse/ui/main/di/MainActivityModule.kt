package com.thebrodyaga.vkurse.ui.main.di

import com.thebrodyaga.vkurse.application.di.scopes.ActivityScope
import com.thebrodyaga.vkurse.data.db.RoomDatabase
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.ui.main.mvp.MainInteractor
import com.thebrodyaga.vkurse.ui.main.mvp.MainPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by Win10
 *         on 16.04.2018.
 */
@Module
class MainActivityModule {

    @ActivityScope
    @Provides
    fun provideMainActivityModel(vkService: VkService, roomDatabase: RoomDatabase)
            : MainInteractor = MainInteractor(vkService, roomDatabase)

    @ActivityScope
    @Provides
    fun provideMainPresenter(mainInteractor: MainInteractor)
            : MainPresenter = MainPresenter(mainInteractor)

}