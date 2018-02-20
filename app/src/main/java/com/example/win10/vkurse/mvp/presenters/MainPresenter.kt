package com.example.win10.vkurse.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.win10.vkurse.mvp.views.MainView
import com.example.win10.vkurse.ui.fragments.VkListFragment

/**
 * Created by Emelyanov.N4 on 19.02.2018.
 */
@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    init {
        viewState.showDefaultFragment(VkListFragment())
    }
}