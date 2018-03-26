package com.thebrodyaga.vkurse.di.modules

import com.thebrodyaga.vkurse.net.VkService
import com.thebrodyaga.vkurse.net.VkurseApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Emelyanov.N4
 *         on 24.02.2018.
 */

@Module(includes = [(ApiModule::class)])
class VkModule {
    @Provides
    @Singleton
    fun provideVkService(vkurseApi: VkurseApi): VkService {
        return VkService(vkurseApi)
    }
}
