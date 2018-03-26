package com.thebrodyaga.vkurse.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.mvp.views.MainView
import com.thebrodyaga.vkurse.ui.fragments.VkListPostsFragment

/**
 * Created by Emelyanov.N4
 *         on 19.02.2018.
 */
@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    init {
        viewState.showDefaultFragment(VkListPostsFragment.FragmentTAG)
    }

    fun onToolbarItemSelected(item: Int?) {
        when (item) {
            R.id.toolbar_settings -> {
                viewState.startSettingActivity()
            }
        }
    }
}