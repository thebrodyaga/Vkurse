package com.thebrodyaga.vkurse.screen.fragments.groupList.mvp

import com.arellomobile.mvp.InjectViewState
import com.thebrodyaga.vkurse.application.di.Injector
import com.thebrodyaga.vkurse.common.clearAndAddAll
import com.thebrodyaga.vkurse.common.debugLogging
import com.thebrodyaga.vkurse.domain.entities.ui.ItemModel
import com.thebrodyaga.vkurse.domain.entities.ui.SearchGroupState.*
import com.thebrodyaga.vkurse.domain.entities.ui.VkSearchGroup
import com.thebrodyaga.vkurse.domain.entities.ui.groupsList.*
import com.thebrodyaga.vkurse.repository.imp.GroupRepository
import com.thebrodyaga.vkurse.screen.base.BasePresenter
import com.thebrodyaga.vkurse.screen.fragments.main.mvp.MainInteractor
import com.thebrodyaga.vkurse.screen.fragments.main.mvp.MainPresenter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by Emelyanov.N4
 *         on 27.03.2018
 */
@InjectViewState
class VkListGroupsPresenter @Inject constructor(private val groupRepository: GroupRepository,
                                                private val router: Router)
    : BasePresenter<VkListGroupsView>() {

    private var newSearchDisposable: Disposable? = null
    private var offsetSearchDisposable: Disposable? = null
    private var mainInteractor: MainInteractor = Injector.plusMainComponent().getMainInteractor()
    private val adapterList = mutableListOf<ItemModel<ItemsForGroupsList>>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        showFavoriteGroups()
        subscribeOnScroll()
    }

    private fun newSearchGroups(query: String) {
        newSearchDisposable?.dispose()
        newSearchDisposable = (groupRepository.newSearchGroups(query)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .switchMap { Observable.just(buildAdapterList(it)) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ viewState.updateList(it) }, {
                    it.printStackTrace()
                    viewState.updateList(adapterList.apply {
                        removeAt(lastIndex)
                        add(HeaderItem())
                    })
                    viewState.showErrorToast()
                }))
    }

    fun offsetSearchGroups() {
        if (offsetSearchDisposable?.isDisposed == false) return
        offsetSearchDisposable = (groupRepository.offsetSearchGroups()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap { Single.just(buildAdapterList(it)) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.updateList(adapterList.apply { add(ProgressItem()) }) }
                .subscribe({
                    debugLogging("offsetSearchGroups successful")
                    viewState.updateList(it)
                }, {
                    it.printStackTrace()
                    viewState.showErrorToast()
                    viewState.updateList(adapterList.apply { removeAt(lastIndex) })
                }))
    }

    fun startSearch(query: String) {
        stopSearch()
        viewState.setSearchText(query)
        newSearchGroups(query)
    }

    fun stopSearch() {
        viewState.setSearchText(null)
        newSearchDisposable?.dispose()
        offsetSearchDisposable?.dispose()
    }

    fun showFavoriteGroups() {
        val result: MutableList<ItemModel<ItemsForGroupsList>> = mutableListOf()
        groupRepository.favoriteGroupsCash.forEach { result.add(VkGroupItem(it)) }
        viewState.updateList(result)
    }

    private fun subscribeOnScroll() {
        unSubscribeOnDestroy(mainInteractor.scrollObservable
                .subscribe { if (it == MainPresenter.ListGroupsFragmentPosition) viewState.scrollTop() })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        router.exit()

    }

    override fun onDestroy() {
        super.onDestroy()
        stopSearch()
    }

    private fun buildAdapterList(searchResult: VkSearchGroup): List<ItemModel<ItemsForGroupsList>> {
        val result = mutableListOf<ItemModel<ItemsForGroupsList>>()
        searchResult.localeList.forEach { result.add(VkGroupItem(it)) }

        adapterList.clearAndAddAll(when (searchResult.state) {
            LOCAL_DATA -> result
            SEARCH_PROGRESS -> result.apply { add(ProgressHeaderItem()) }
            SEARCH_RESULT -> result.apply {
                add(HeaderItem())
                searchResult.fromApiList.forEach { result.add(VkGroupItem(it)) }
            }
        })
        return adapterList
    }
}