package com.thebrodyaga.vkurse.ui.main.dagger

import com.thebrodyaga.vkurse.data.db.RoomDatabase
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.ui.main.mvp.models.MainActivityModel
import com.thebrodyaga.vkurse.ui.main.mvp.presenters.MainPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by Win10
 *         on 16.04.2018.
 */
@Module
class MainActivityModule {

    @Provides
    fun provideMainActivityModel(vkService: VkService, roomDatabase: RoomDatabase)
            : MainActivityModel = MainActivityModel(vkService, roomDatabase)

    @Provides
    fun provideMainePresenter(mainActivityModel: MainActivityModel)
            : MainPresenter = MainPresenter(mainActivityModel)

}