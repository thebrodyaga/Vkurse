package com.thebrodyaga.vkurse.application.di.modules

import dagger.Module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import java.util.HashMap

/**
 * Created by admin
 *         on 06.08.2018.
 */
@Module
class LocalCiceroneHolder {
    private val containers: HashMap<String, Cicerone<Router>> = HashMap()

    fun getCicerone(containerTag: String): Cicerone<Router> {
        if (!containers.containsKey(containerTag)) {
            containers[containerTag] = Cicerone.create()
        }
        return containers[containerTag]!!
    }
}