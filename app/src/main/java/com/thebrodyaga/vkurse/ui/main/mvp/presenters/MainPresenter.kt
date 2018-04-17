package com.thebrodyaga.vkurse.ui.main.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.ui.main.mvp.views.MainView
import com.thebrodyaga.vkurse.ui.list.posts.VkListPostsFragment
import com.thebrodyaga.vkurse.ui.main.mvp.models.MainActivityModel

/**
 * Created by Emelyanov.N4
 *         on 19.02.2018.
 */
@InjectViewState
class MainPresenter(private val mainActivityModel: MainActivityModel) : MvpPresenter<MainView>() {
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

    fun toggleSearchIcon(isVisible: Boolean) {
        viewState.toggleSearchIcon(isVisible)
    }
}