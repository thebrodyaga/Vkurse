package com.thebrodyaga.vkurse.domain.entities.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.thebrodyaga.vkobjects.groups.Group

/**
 * Created by Win10
 *         on 11.04.2018.
 */

@Entity(tableName = VkGroup.tableName)
data class VkGroup(@Ignore private val group: Group) {
    constructor() : this(Group())

    @PrimaryKey
    var id: Int = group.id ?: 0
    var name: String? = group.name
    var screenName: String? = group.screenName
    var type: String? = group.type?.toString() //GroupType enum
    var photo50: String? = group.photo50
    var photo100: String? = group.photo100
    var photo200: String? = group.photo200

    /*(
        @PrimaryKey
        var id: Int,
        var name: String? = null,
        var screenName: String? = null,
        var type: String? = null,  //GroupType enum
        var photo50: String? = null,
        var photo100: String? = null,
        var photo200: String? = null)*/

    companion object {
        const val tableName = "VkGroup"
    }
}