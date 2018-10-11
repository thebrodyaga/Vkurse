package com.thebrodyaga.vkurse.screen.fragments.groupList


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.debugLogging
import com.thebrodyaga.vkurse.common.hideKeyboardFrom
import com.thebrodyaga.vkurse.domain.entities.ui.ItemModel
import com.thebrodyaga.vkurse.domain.entities.ui.groupsList.ItemsForGroupsList
import com.thebrodyaga.vkurse.screen.adapters.VkGroupsAdapter
import com.thebrodyaga.vkurse.screen.base.BaseFragment
import com.thebrodyaga.vkurse.screen.fragments.groupList.mvp.VkListGroupsPresenter
import com.thebrodyaga.vkurse.screen.fragments.groupList.mvp.VkListGroupsView
import com.thebrodyaga.vkurse.screen.utils.EndlessRecyclerOnScrollListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_vk_list_groups.*
import javax.inject.Inject


class VkListGroupsFragment : BaseFragment(), VkListGroupsView {
    override fun setSearchText(query: String?) {
        debugLogging("setSearchText query = $query")
        currentQuery = query
    }

    override fun scrollTop() {
        debugLogging("scrollTop VkListPostsFragment")
        recyclerView.scrollToPosition(0)
    }

    override fun updateList(searchResponse: List<ItemModel<ItemsForGroupsList>>) {
        debugLogging("updateList")
        adapter.submitList(ArrayList(searchResponse))
    }

    @Inject
    @InjectPresenter()
    lateinit var presenter: VkListGroupsPresenter
    private val adapter = VkGroupsAdapter()
    private var searchDisposable: Disposable? = null
    private var currentQuery: CharSequence? = null

    @ProvidePresenter
    fun provideSearchPresenter(): VkListGroupsPresenter = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_vk_list_groups, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindRecyclerView(recyclerView)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }

    private fun bindRecyclerView(recyclerView: RecyclerView) {
        debugLogging("setupRecyclerView")
        val layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val scrollListener = EndlessRecyclerOnScrollListener(layoutManager)
        scrollListener.setOnLoadMore { presenter.offsetSearchGroups() }
        recyclerView.addOnScrollListener(scrollListener)
        adapter.setOnListItemClickListener { item, position, _ -> }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.main_toolbar, menu)
        val searchItem = menu.findItem(R.id.toolbar_search)
        val searchView = searchItem.actionView as SearchView
        searchView.post {
            searchView.maxWidth = Int.MAX_VALUE
            if (currentQuery != null) {
                searchView.isIconified = false
                searchItem.expandActionView()
                searchView.setQuery(currentQuery, false)
            } else searchView.setQuery("", false)
            searchView.clearFocus()
            searchDisposable = RxSearchView.queryTextChanges(searchView)
                    .skip(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (it.isNotEmpty()) presenter.startSearch(it.toString())
                        else {
                            presenter.stopSearch()
                            presenter.showFavoriteGroups()
                        }
                    }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchDisposable?.dispose()
        hideKeyboardFrom(this)
    }

    override fun showErrorToast() {
        debugLogging("showErrorToast")
        showToast(getString(R.string.error_toast))
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}