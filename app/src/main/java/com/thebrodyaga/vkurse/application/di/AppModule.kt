package com.thebrodyaga.vkurse.application.di

import android.app.Application
import android.content.Context
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkurse.application.di.modules.*
import com.thebrodyaga.vkurse.data.db.RoomDatabase
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.data.net.VkurseApi
import com.thebrodyaga.vkurse.repository.GroupRepository
import com.thebrodyaga.vkurse.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Win10
 *         on 16.04.2018.
 */
@Module(includes = [VkurseApiModule::class, RoomModule::class, PicassoModule::class,
    LocalCiceroneHolder::class, NavigationModule::class])
class AppModule {

    @Provides
    @Singleton
    fun providePostRepository(vkService: VkService, roomDatabase: RoomDatabase): PostRepository {
        return PostRepository(vkService, roomDatabase)
    }

    @Provides
    @Singleton
    fun provideGroupRepository(vkService: VkService, roomDatabase: RoomDatabase): GroupRepository {
        return GroupRepository(vkService, roomDatabase)
    }

    @Provides
    @Singleton
    fun provideVkService(vkurseApi: VkurseApi): VkService {
        return VkService(vkurseApi)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }
}