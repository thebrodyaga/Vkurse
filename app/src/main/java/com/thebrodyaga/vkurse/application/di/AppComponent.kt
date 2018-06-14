package com.thebrodyaga.vkurse.application.di

import android.app.Application
import com.squareup.picasso.Picasso
import com.thebrodyaga.vkurse.application.App
import com.thebrodyaga.vkurse.data.db.RoomDatabase
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.screen.main.di.MainComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Created by Emelyanov.N4
 *         on 16.02.2018.
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }

    fun getPicasso(): Picasso
    fun getVkService(): VkService
    fun getRoom(): RoomDatabase
    fun plusMainComponent(): MainComponent
}