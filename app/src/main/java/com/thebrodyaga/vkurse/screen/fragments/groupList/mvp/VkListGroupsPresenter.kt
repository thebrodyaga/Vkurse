package com.thebrodyaga.vkurse.screen.fragments.groupList.mvp

import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkobjects.groups.Group
import com.thebrodyaga.vkurse.application.di.Injector
import com.thebrodyaga.vkurse.screen.base.BasePresenter
import com.thebrodyaga.vkurse.screen.fragments.main.mvp.MainInteractor
import com.thebrodyaga.vkurse.screen.fragments.main.mvp.MainPresenter.Companion.ListGroupsFragmentPosition
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by Emelyanov.N4
 *         on 28.03.2018
 */
@InjectViewState
class VkListGroupsPresenter @Inject constructor(private val router: Router)
    : BasePresenter<VkListGroupsView>() {
    private var mainInteractor: MainInteractor = Injector.plusMainComponent().getMainInteractor()
    private var fullList = listOf(Group(), Group(), Group(), Group(), Group(), Group(), Group(),
            Group(), Group(), Group(), Group(), Group(), Group(), Group(), Group(), Group(), Group(),
            Group(), Group(), Group())

    override fun onFirstViewAttach() {
        getFullGroups()
        subscribeOnScroll()
    }

    fun getFullGroups() {
        viewState.showFullGroupsList(fullList)
    }

    fun getFilteredList(query: String) {
        viewState.showFilteredGroupsList(/*ullList.filter { it.name.startsWith(query) }*/fullList)
    }

    private fun subscribeOnScroll() {
        unSubscribeOnDestroy(mainInteractor.scrollObservable
                .subscribe { if (it == ListGroupsFragmentPosition) viewState.scrollTop() })
    }

    override fun onBackPressed() {
        router.exit()
    }
}