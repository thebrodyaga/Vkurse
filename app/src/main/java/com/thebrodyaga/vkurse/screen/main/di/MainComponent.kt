package com.thebrodyaga.vkurse.screen.main.di

import com.thebrodyaga.vkurse.application.di.scopes.ActivityScope
import com.thebrodyaga.vkurse.screen.main.mvp.MainInteractor
import dagger.Subcomponent

/**
 * Created by Emelyanov917
 *         on 14.06.2018.
 */
@Subcomponent(modules = [(MainModule::class)])
@ActivityScope
interface MainComponent {
    fun getMainInteractor(): MainInteractor
}