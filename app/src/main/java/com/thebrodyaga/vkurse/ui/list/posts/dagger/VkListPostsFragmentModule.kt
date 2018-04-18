package com.thebrodyaga.vkurse.ui.list.posts.dagger

import com.thebrodyaga.vkurse.application.di.scopes.PerFragment
import com.thebrodyaga.vkurse.ui.list.posts.mvp.VkListPostsPresenter
import com.thebrodyaga.vkurse.ui.main.mvp.MainActivityModel
import dagger.Module
import dagger.Provides

/**
 * Created by Win10
 *         on 16.04.2018.
 */
@Module
class VkListPostsFragmentModule {
    @PerFragment
    @Provides
    fun provideVkListPostsPresenter(mainActivityModel: MainActivityModel)
            : VkListPostsPresenter = VkListPostsPresenter(mainActivityModel)
}