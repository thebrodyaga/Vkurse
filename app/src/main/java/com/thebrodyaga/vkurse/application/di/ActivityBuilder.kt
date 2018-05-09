package com.thebrodyaga.vkurse.application.di

import com.thebrodyaga.vkurse.application.di.scopes.ActivityScope
import com.thebrodyaga.vkurse.ui.main.MainActivity
import com.thebrodyaga.vkurse.ui.main.di.MainActivityModule
import com.thebrodyaga.vkurse.ui.main.di.MainFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Win10
 *         on 16.04.2018.
 */
@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class, MainFragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity
}