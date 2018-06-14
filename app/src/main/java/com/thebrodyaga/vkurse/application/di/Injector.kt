package com.thebrodyaga.vkurse.application.di

import com.thebrodyaga.vkurse.application.App
import com.thebrodyaga.vkurse.screen.main.di.MainComponent

/**
 * Created by Emelyanov917
 *         on 14.06.2018.
 */
object Injector {
    private var mainComponent: MainComponent? = null

    fun plusMainComponent(): MainComponent {
        // always get only one instance
        if (mainComponent == null) {
            // start lifecycle of chatComponent
            mainComponent = App.appComponent.plusMainComponent()
        }
        return mainComponent as MainComponent
    }

    fun clearMainComponent() {
        // end lifecycle of chatComponent
        mainComponent = null
    }
}