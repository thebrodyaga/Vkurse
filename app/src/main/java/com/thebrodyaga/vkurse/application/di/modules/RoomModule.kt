package com.thebrodyaga.vkurse.application.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.thebrodyaga.vkurse.data.db.RoomDatabase
import com.thebrodyaga.vkurse.common.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Win10
 *         on 12.04.2018.
 */
@Module()
class RoomModule {
    @Provides
    @Singleton
    fun provideRoom(context: Context): RoomDatabase {
        return Room.databaseBuilder(context, RoomDatabase::class.java, DATABASE_NAME).build()
    }
}