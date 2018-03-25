package com.thebrodyaga.vkurse.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.thebrodyaga.vkurse.mvp.views.SearchView

/**
 * Created by Win10 on 25.03.2018.
 */

@InjectViewState
class SearchPresenter:MvpPresenter<SearchView>(), android.support.v7.widget.SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val SearchPresenterTAG: String = "SearchPresenterTag"
    }
}
