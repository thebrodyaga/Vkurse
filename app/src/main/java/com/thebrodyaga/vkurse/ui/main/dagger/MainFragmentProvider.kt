package com.thebrodyaga.vkurse.ui.main.dagger

import com.thebrodyaga.vkurse.application.di.scopes.PerActivity
import com.thebrodyaga.vkurse.application.di.scopes.PerFragment
import com.thebrodyaga.vkurse.ui.list.groups.VkListGroupsFragment
import com.thebrodyaga.vkurse.ui.list.groups.dagger.VkListGroupsFragmentModule
import com.thebrodyaga.vkurse.ui.list.posts.VkListPostsFragment
import com.thebrodyaga.vkurse.ui.list.posts.dagger.VkListPostsFragmentModule
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