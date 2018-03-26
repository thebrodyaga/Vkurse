package com.thebrodyaga.vkurse.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Emelyanov.N4
 *         on 24.02.2018.
 */
@Module
class ContextModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }
}