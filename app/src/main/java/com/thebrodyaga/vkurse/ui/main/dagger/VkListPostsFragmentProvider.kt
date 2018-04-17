package com.thebrodyaga.vkurse.ui.main.dagger

import com.thebrodyaga.vkurse.ui.list.posts.VkListPostsFragment
import com.thebrodyaga.vkurse.ui.list.posts.dagger.VkListPostsFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Win10
 *         on 16.04.2018.
 */
@Module
abstract class VkListPostsFragmentProvider {
    @ContributesAndroidInjector(modules = [VkListPostsFragmentModule::class])
    abstract fun provideVkListPostsFragmentFactory(): VkListPostsFragment
}