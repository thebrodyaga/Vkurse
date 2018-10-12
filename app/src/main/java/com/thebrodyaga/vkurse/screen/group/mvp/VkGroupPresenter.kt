package com.thebrodyaga.vkurse.screen.group.mvp

import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.screen.base.BasePresenter

/**
 * Created by admin
 *         on 05.07.2018.
 */

@InjectViewState
class VkGroupPresenter(private val vkGroupInteractor: VkGroupInteractor)
    : BasePresenter<VkGroupView>() {

    var currentGroup: Int = 0
        set(value) {
            if (value == 0) viewState.errorFinish()
        }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        vkGroupInteractor.loadFullGroup(currentGroup)
    }
}