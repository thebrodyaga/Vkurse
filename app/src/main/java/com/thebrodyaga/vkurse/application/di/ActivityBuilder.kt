package com.thebrodyaga.vkurse.application.di

import com.thebrodyaga.vkurse.application.di.scopes.ActivityScope
import com.thebrodyaga.vkurse.screen.imageSlider.ImageSliderActivity
import com.thebrodyaga.vkurse.screen.imageSlider.di.ImageSliderFragmentProvider
import com.thebrodyaga.vkurse.screen.main.MainActivity
import com.thebrodyaga.vkurse.screen.main.di.MainFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Win10
 *         on 16.04.2018.
 */
@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ImageSliderFragmentProvider::class])
    abstract fun bindImageSliderActivity(): ImageSliderActivity
}