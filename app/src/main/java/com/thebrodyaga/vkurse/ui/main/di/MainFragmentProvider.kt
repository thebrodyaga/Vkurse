package com.thebrodyaga.vkurse.ui.main.di

import com.thebrodyaga.vkurse.application.di.scopes.PerFragment
import com.thebrodyaga.vkurse.ui.groupList.VkListGroupsFragment
import com.thebrodyaga.vkurse.ui.groupList.di.VkListGroupsFragmentModule
import com.thebrodyaga.vkurse.ui.postList.VkListPostsFragment
import com.thebrodyaga.vkurse.ui.postList.di.VkListPostsFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Win10
 *         on 16.04.2018.
 */
@Module
abstract class MainFragmentProvider {
    @PerFragment
    @ContributesAndroidInjector(modules = [VkListPostsFragmentModule::class])
    abstract fun provideVkListPostsFragmentFactory(): VkListPostsFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [VkListGroupsFragmentModule::class])
    abstract fun provideVkListGroupsFragmentFactory(): VkListGroupsFragment
}