package com.thebrodyaga.vkurse.application.di

import com.thebrodyaga.vkurse.application.di.scopes.PerActivity
import com.thebrodyaga.vkurse.ui.main.MainActivity
import com.thebrodyaga.vkurse.ui.main.dagger.MainActivityModule
import com.thebrodyaga.vkurse.ui.main.dagger.MainFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Win10
 *         on 16.04.2018.
 */
@Module
abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class, MainFragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity
}