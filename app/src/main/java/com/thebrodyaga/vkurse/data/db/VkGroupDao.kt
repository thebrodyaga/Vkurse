package com.thebrodyaga.vkurse.data.db

import android.arch.persistence.room.*
import com.thebrodyaga.vkurse.domain.entities.room.VkGroup
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Win10
 *         on 12.04.2018.
 */

@Dao
interface VkGroupDao {
    @Query("SELECT * FROM ${VkGroup.tableName}")
    fun getFlowableGroups(): Flowable<List<VkGroup>>

    @Query("SELECT * FROM ${VkGroup.tableName}")
    fun getSingleGroups(): Single<List<VkGroup>>

    @Query("SELECT * FROM ${VkGroup.tableName} WHERE id = :id")
    fun getGroupById(id: Int): VkGroup

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGroups(vkGroup: VkGroup)

    @Delete
    fun deleteGroup(group: VkGroup)

    @Query("DELETE FROM ${VkGroup.tableName}")
    fun deleteAllGroups()
}