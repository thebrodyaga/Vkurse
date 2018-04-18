package com.thebrodyaga.vkurse.ui.list.groups.dagger

import com.thebrodyaga.vkurse.application.di.scopes.PerFragment
import com.thebrodyaga.vkurse.ui.list.groups.mvp.SearchGroupsPresenter
import com.thebrodyaga.vkurse.ui.list.groups.mvp.VkListGroupsPresenter
import com.thebrodyaga.vkurse.ui.main.mvp.models.MainActivityModel
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
    fun provideListGroupsPresenter(mainActivityModel: MainActivityModel)
            : VkListGroupsPresenter = VkListGroupsPresenter(mainActivityModel)

    @PerFragment
    @Provides
    fun provideSearchGroupsPresenter(mainActivityModel: MainActivityModel)
            : SearchGroupsPresenter = SearchGroupsPresenter(mainActivityModel)
}