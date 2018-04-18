package com.thebrodyaga.vkurse.ui.main.mvp.models

import android.arch.persistence.room.RoomDatabase
import com.thebrodyaga.vkurse.application.di.scopes.PerActivity
import com.thebrodyaga.vkurse.data.net.VkService
import javax.inject.Inject

/**
 * Created by Win10
 *         on 17.04.2018.
 */
@PerActivity
class MainActivityModel @Inject constructor(
        private val vkService: VkService,
        private val roomDatabase: RoomDatabase) {

    
}