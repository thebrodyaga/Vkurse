package com.thebrodyaga.vkurse.screen.fragments.main.di

import com.thebrodyaga.vkurse.application.di.scopes.SecondFragment
import com.thebrodyaga.vkurse.screen.fragments.groupList.VkListGroupsFragment
import com.thebrodyaga.vkurse.screen.fragments.postList.VkListPostsFragment
import com.thebrodyaga.vkurse.screen.fragments.postList.di.VkListPostsProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by admin
 *         on 12/10/2018.
 */
@Module
abstract class MainFragmentProvider {

    @SecondFragment
    @ContributesAndroidInjector(modules = [VkListPostsProvider::class])
    abstract fun provideVkListPostsFragment(): VkListPostsFragment

    @SecondFragment
    @ContributesAndroidInjector()
    abstract fun provideVkListGroupsFragment(): VkListGroupsFragment
}