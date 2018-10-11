package com.thebrodyaga.vkurse.repository.imp

import com.thebrodyaga.vkurse.data.db.RoomDatabase
import com.thebrodyaga.vkurse.data.net.VkService

/**
 * Created by Win10
 *         on 09.05.2018.
 */
abstract class BaseRepository(val vkService: VkService,
                              val roomDatabase: RoomDatabase) {
}