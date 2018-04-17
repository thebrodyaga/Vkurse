package com.thebrodyaga.vkurse.models.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.thebrodyaga.vkobjects.groups.Group

/**
 * Created by Win10
 *         on 11.04.2018.
 */

@Entity(tableName = VkGroup.tableName)
data class VkGroup(@Ignore private val group: Group?) {
    constructor() : this(null)

    @PrimaryKey
    var id: Int? = group?.id
    var name: String? = group?.name
    var screenName: String? = group?.screenName
    var type: String? = group?.type?.toString() //GroupType enum
    var photo50: String? = group?.photo50
    var photo100: String? = group?.photo100
    var photo200: String? = group?.photo200

    companion object {
        const val tableName = "VkGroup"
    }
}