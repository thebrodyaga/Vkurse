package com.thebrodyaga.vkurse.application.di.modules

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

/**
 * Created by admin
 *         on 06.08.2018.
 */

@Module
class NavigationModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    internal fun provideRouter(): Router {
        return cicerone.router
    }

    @Provides
    @Singleton
    internal fun provideNavigatorHolder(): NavigatorHolder {
        return cicerone.navigatorHolder
    }
}
