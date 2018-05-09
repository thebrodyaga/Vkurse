package com.thebrodyaga.vkurse.screen.main.di

import com.thebrodyaga.vkurse.application.di.scopes.PerFragment
import com.thebrodyaga.vkurse.screen.groupList.VkListGroupsFragment
import com.thebrodyaga.vkurse.screen.postList.VkListPostsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Win10
 *         on 16.04.2018.
 */
@Module
abstract class MainFragmentProvider {
    @PerFragment
    @ContributesAndroidInjector()
    abstract fun provideVkListPostsFragment(): VkListPostsFragment

    @PerFragment
    @ContributesAndroidInjector()
    abstract fun provideVkListGroupsFragment(): VkListGroupsFragment
}