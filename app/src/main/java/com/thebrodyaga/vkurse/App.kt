package com.thebrodyaga.vkurse

import android.app.Application
import com.thebrodyaga.vkurse.di.AppComponent
import com.thebrodyaga.vkurse.di.DaggerAppComponent
import com.thebrodyaga.vkurse.di.modules.ContextModule

/**
 * Created by Win10 on 18.07.2017.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .contextModule(ContextModule(this))
                .build()
    }


    companion object {
        lateinit var appComponent: AppComponent
    }

}
