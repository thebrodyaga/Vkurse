package com.thebrodyaga.vkurse.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.thebrodyaga.vkurse.common.DATABASE_VERSION
import com.thebrodyaga.vkurse.models.room.VkGroup

/**
 * Created by Win10
 *         on 12.04.2018.
 */
@Database(entities = [VkGroup::class], version = DATABASE_VERSION, exportSchema = false)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun getGroupDao(): VkGroupDao
}