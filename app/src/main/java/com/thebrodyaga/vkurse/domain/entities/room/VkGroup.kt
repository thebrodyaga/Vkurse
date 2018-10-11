package com.thebrodyaga.vkurse.domain.entities.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkobjects.groups.GroupIsClosed
import com.thebrodyaga.vkobjects.groups.GroupType

/**
 * Created by Win10
 *         on 11.04.2018.
 */

@Entity(tableName = VkGroup.tableName)
data class VkGroup(@PrimaryKey
                   val id: Int,
                   val name: String,
                   val screenName: String,
                   val isClosed: GroupIsClosed,
                   val type: GroupType,
                   val photo50: String,
                   val photo100: String,
                   val photo200: String,
                   val deactivated: String? = null) {

    companion object {
        const val tableName = "VkGroup"
        fun newInstance(group: Group): VkGroup =
                group.run { VkGroup(id, name, screenName, isClosed, type, photo50, photo100, photo200, deactivated) }

    }
}