package com.thebrodyaga.vkurse.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.thebrodyaga.vkurse.common.DATABASE_VERSION
import com.thebrodyaga.vkurse.domain.entities.room.VkGroup

/**
 * Created by Win10
 *         on 12.04.2018.
 */
@TypeConverters(value = [GroupIsClosedConverters::class, GroupTypeConverters::class])
@Database(entities = [VkGroup::class], version = DATABASE_VERSION, exportSchema = false)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun getGroupDao(): VkGroupDao
}