package com.thebrodyaga.vkurse.screen.activities.main

import com.thebrodyaga.vkurse.application.di.scopes.PerFragment
import com.thebrodyaga.vkurse.screen.fragments.main.MainFragment
import com.thebrodyaga.vkurse.screen.fragments.groupList.VkListGroupsFragment
import com.thebrodyaga.vkurse.screen.fragments.main.di.MainFragmentProvider
import com.thebrodyaga.vkurse.screen.fragments.postList.VkListPostsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Win10
 *         on 16.04.2018.
 */
@Module
abstract class MainProvider {

    @PerFragment
    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    abstract fun provideMainFragment(): MainFragment
}