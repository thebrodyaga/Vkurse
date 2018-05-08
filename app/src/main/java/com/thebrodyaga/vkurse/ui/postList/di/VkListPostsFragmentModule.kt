package com.thebrodyaga.vkurse.ui.postList.di

import com.thebrodyaga.vkurse.application.di.scopes.PerFragment
import com.thebrodyaga.vkurse.ui.postList.mvp.VkListPostsPresenter
import com.thebrodyaga.vkurse.ui.main.mvp.MainInteractor
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
    fun provideVkListPostsPresenter(mainInteractor: MainInteractor)
            : VkListPostsPresenter = VkListPostsPresenter(mainInteractor)
}