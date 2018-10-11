package com.thebrodyaga.vkurse.data.db

import android.arch.persistence.room.TypeConverter
import com.thebrodyaga.vkobjects.groups.GroupIsClosed
import com.thebrodyaga.vkobjects.groups.GroupType


/**
 * Created by admin
 *         on 09.08.2018.
 */

class GroupIsClosedConverters {
    @TypeConverter
    fun fromString(value: Int): GroupIsClosed {
        return GroupIsClosed.valueOf(value.toString())
    }

    @TypeConverter
    fun fromData(groupIsClosed: GroupIsClosed): Int {
        return groupIsClosed.value
    }
}

class GroupTypeConverters {
    @TypeConverter
    fun fromString(value: String): GroupType {
        return GroupType.valueOf(value)
    }

    @TypeConverter
    fun fromData(groupIsClosed: GroupType): String {
        return groupIsClosed.value
    }
}