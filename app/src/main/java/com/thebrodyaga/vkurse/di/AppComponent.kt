package com.thebrodyaga.vkurse.di

import com.thebrodyaga.vkurse.di.modules.PicassoModule
import com.thebrodyaga.vkurse.di.modules.VkModule
import com.thebrodyaga.vkurse.mvp.presenters.VkListPostsPresenter
import com.thebrodyaga.vkurse.mvp.presenters.SearchGroupsPresenter
import com.thebrodyaga.vkurse.ui.adapters.VkGroupsAdapter
import com.thebrodyaga.vkurse.ui.adapters.VkPostsAdapter
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Emelyanov.N4
 *         on 16.02.2018.
 */
@Singleton
@Component(modules = [(PicassoModule::class), (VkModule::class)])
interface AppComponent {
    fun inject(vkListPostsPresenter: VkListPostsPresenter)
    fun inject(searchGroupsPresenter: SearchGroupsPresenter)
    fun inject(baseAdapter: VkPostsAdapter)
    fun inject(baseAdapter: VkGroupsAdapter)
}