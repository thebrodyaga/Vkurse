package com.thebrodyaga.vkurse.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.thebrodyaga.vkurse.models.room.VkGroup
import io.reactivex.Flowable

/**
 * Created by Win10
 *         on 12.04.2018.
 */

@Dao
interface VkGroupDao {
    @Query("SELECT * FROM ${VkGroup.tableName}")
    fun getAllGroups(): Flowable<List<VkGroup>>

    @Query("SELECT * FROM ${VkGroup.tableName} WHERE id = :id")
    fun getGroupById(id: Int): VkGroup

    @Insert
    fun insertGroups(vararg groups: VkGroup)

    @Delete
    fun deleteGroup(group: VkGroup)

    @Query("DELETE FROM ${VkGroup.tableName}")
    fun deleteAllGroups()
}