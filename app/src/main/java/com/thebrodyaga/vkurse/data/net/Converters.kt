/*
package com.thebrodyaga.vkurse.data.net

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.thebrodyaga.vkobjects.base.BoolInt
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

*/
/**
 * Created by admin
 *         on 23.09.2018.
 *//*


@Singleton
class BoolIntTypeAdapter @Inject constructor() : JsonDeserializer<BoolInt> {
    override fun deserialize(json: JsonElement, typeOfT: Type,
                             context: JsonDeserializationContext): BoolInt? {
        if (json is JsonPrimitive) {
            if (json.isBoolean)
                return when (json.asBoolean) {
                    false -> BoolInt.NO
                    true -> BoolInt.YES
                }
            if (json.isNumber)
                return when (json.asInt) {
                    0 -> BoolInt.NO
                    1 -> BoolInt.YES
                    else -> null
                }
            return null
        }
        return null
    }
}*/
