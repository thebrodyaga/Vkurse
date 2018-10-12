package com.thebrodyaga.vkurse.screen.fragments.postList.di

import com.thebrodyaga.vkurse.application.di.scopes.SecondFragment
import com.thebrodyaga.vkurse.data.db.RoomDatabase
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.repository.PostRepository
import com.thebrodyaga.vkurse.repository.imp.PostRepositoryImp
import dagger.Module
import dagger.Provides

/**
 * Created by admin
 *         on 12/10/2018.
 */
@Module
class VkListPostsProvider {
    @SecondFragment
    @Provides
    fun providePostRepository(vkService: VkService, roomDatabase: RoomDatabase): PostRepository =
            PostRepositoryImp(vkService, roomDatabase)

}