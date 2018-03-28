package com.thebrodyaga.vkurse.mvp.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.mvp.views.ToolbarSearchView
import android.support.v7.widget.SearchView as AndroidSearchView

/**
 * Created by Win10
 *         on 25.03.2018.
 */

@InjectViewState
class ToolbarSearchPresenter : MvpPresenter<ToolbarSearchView>(), AndroidSearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        searchControl(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchControl(newText)
        return false
    }

    private fun searchControl(query: String?) {
        if (query != null && query.isNotBlank()) viewState.needSearch(query)
        else viewState.notNeedSearch()
    }

    companion object {
        const val SearchPresenterTAG: String = "SearchPresenterTag"
    }
}
