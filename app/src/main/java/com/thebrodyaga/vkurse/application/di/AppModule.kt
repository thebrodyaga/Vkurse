package com.thebrodyaga.vkurse.application.di

import android.app.Application
import android.content.Context
import com.thebrodyaga.vkurse.application.di.modules.PicassoModule
import com.thebrodyaga.vkurse.application.di.modules.RoomModule
import com.thebrodyaga.vkurse.application.di.modules.VkurseApiModule
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.data.net.VkurseApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Win10
 *         on 16.04.2018.
 */
@Module(includes = [VkurseApiModule::class, RoomModule::class, PicassoModule::class])
class AppModule {

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