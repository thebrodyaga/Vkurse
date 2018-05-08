package com.thebrodyaga.vkurse.ui.groupList.di

import com.thebrodyaga.vkurse.application.di.scopes.PerFragment
import com.thebrodyaga.vkurse.ui.groupList.mvp.SearchGroupsPresenter
import com.thebrodyaga.vkurse.ui.groupList.mvp.VkListGroupsPresenter
import com.thebrodyaga.vkurse.ui.main.mvp.MainInteractor
import dagger.Module
import dagger.Provides

/**
 * Created by Win10
 *         on 16.04.2018.
 */
@Module
class VkListGroupsFragmentModule {
    @PerFragment
    @Provides
    fun provideListGroupsPresenter(mainInteractor: MainInteractor)
            : VkListGroupsPresenter = VkListGroupsPresenter(mainInteractor)

    @PerFragment
    @Provides
    fun provideSearchGroupsPresenter(mainInteractor: MainInteractor)
            : SearchGroupsPresenter = SearchGroupsPresenter(mainInteractor)
}