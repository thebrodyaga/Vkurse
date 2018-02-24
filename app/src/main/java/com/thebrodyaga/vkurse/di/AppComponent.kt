package com.thebrodyaga.vkurse.di

import android.content.Context
import com.thebrodyaga.vkurse.di.modules.ContextModule
import com.thebrodyaga.vkurse.di.modules.VkModule
import com.thebrodyaga.vkurse.mvp.presenters.VkListPresenter
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Emelyanov.N4 on 16.02.2018.
 */
@Singleton
@Component(modules = [(ContextModule::class), (VkModule::class)])
interface AppComponent {
    fun getContext(): Context
    fun inject(vkListPresenter: VkListPresenter)
}