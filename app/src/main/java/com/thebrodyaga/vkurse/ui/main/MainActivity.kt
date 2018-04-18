package com.thebrodyaga.vkurse.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.ui.base.DaggerAppCompatActivity
import com.thebrodyaga.vkurse.ui.chat.ChatFragment
import com.thebrodyaga.vkurse.ui.list.groups.VkListGroupsFragment
import com.thebrodyaga.vkurse.ui.list.posts.VkListPostsFragment
import com.thebrodyaga.vkurse.ui.main.mvp.presenters.MainPresenter
import com.thebrodyaga.vkurse.ui.main.mvp.presenters.NavigationBarPresenter
import com.thebrodyaga.vkurse.ui.main.mvp.presenters.ScrollToTopPresenter
import com.thebrodyaga.vkurse.ui.main.mvp.presenters.ToolbarSearchPresenter
import com.thebrodyaga.vkurse.ui.main.mvp.views.MainView
import com.thebrodyaga.vkurse.ui.main.mvp.views.NavigationBarView
import com.thebrodyaga.vkurse.ui.main.mvp.views.ScrollToTopView
import com.thebrodyaga.vkurse.ui.main.mvp.views.ToolbarSearchView
import com.thebrodyaga.vkurse.ui.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.support.v7.widget.SearchView as AndroidSearchView


class MainActivity : DaggerAppCompatActivity(), NavigationBarView, MainView,
        ScrollToTopView, ToolbarSearchView {

    @InjectPresenter
    lateinit var navigationBarPresenter: NavigationBarPresenter
    @Inject
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter
    @InjectPresenter(type = PresenterType.GLOBAL, tag = ScrollToTopPresenter.ScrollToTopPresenterTAG)
    lateinit var scrollToTopPresenter: ScrollToTopPresenter
    @InjectPresenter(type = PresenterType.GLOBAL, tag = ToolbarSearchPresenter.SearchPresenterTAG)
    lateinit var toolbarSearchPresenter: ToolbarSearchPresenter
    private var searchItem: MenuItem? = null
    //  при смене конфигурации toggleSearchIcon() вызывается раньше чем onCreateOptionsMenu()
    //TODO поискать как сделать по-красоте
    private var isSearchItemVisible = false

    @ProvidePresenter
    fun providePresenter(): MainPresenter = mainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        AHBottomNavigationAdapter(this, R.menu.main_bottom_bar).setupWithBottomNavigation(bottomBar)
        bottomBar.accentColor = ContextCompat.getColor(this, R.color.primary)
        bottomBar.setOnTabSelectedListener { position, wasSelected ->
            if (wasSelected) scrollToTopPresenter.onBottomBarReClick(position)
            else navigationBarPresenter.onBottomBarClick(position)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        mainPresenter.onToolbarItemSelected(item?.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar, menu)
        searchItem = menu?.findItem(R.id.toolbar_search)
        searchItem?.isVisible = isSearchItemVisible
        val searchView: AndroidSearchView? = searchItem?.actionView as AndroidSearchView?
        searchView?.setOnQueryTextListener(toolbarSearchPresenter)
        return true
    }

    //<editor-fold desc="NavigationBarView">
    override fun showListPostsFragment() {
        Log.d(DEBUG_TAG, "showListPostsFragment")
        searchItem?.collapseActionView()
        managingFragment(VkListPostsFragment.FragmentTAG)
    }

    override fun showListGroupsFragment() {
        Log.d(DEBUG_TAG, "showListGroupsFragment")
        managingFragment(VkListGroupsFragment.FragmentTAG)
    }

    override fun showChatFragment() {
        Log.d(DEBUG_TAG, "showChatFragment")
        searchItem?.collapseActionView()
        managingFragment(ChatFragment.FragmentTAG)
    }
    //</editor-fold>

    //<editor-fold desc="MainView">
    override fun showDefaultFragment(fragmentTag: String) {
        Log.d(DEBUG_TAG, "showDefaultFragment")
        managingFragment(fragmentTag)
    }

    override fun startSettingActivity() {
        startActivity(Intent(this, SettingActivity::class.java))
    }

    override fun toggleSearchIcon(isVisible: Boolean) {
        Log.i(DEBUG_TAG, "toggleSearchIcon isVisible = $isVisible")
        searchItem?.isVisible = isVisible
        isSearchItemVisible = isVisible
    }
    //</editor-fold>

    override fun scrollTop(menuPosition: Int) {
        Log.d(DEBUG_TAG, "scrollTop MainActivity")
        myAppBar.setExpanded(true)
        return
    }

    //<editor-fold desc="ToolbarSearchView">
    override fun needSearch(query: String) {
        Log.i("DebugTag", "needSearch MainActivity")
    }

    override fun notNeedSearch() {
        Log.i(DEBUG_TAG, "notNeedSearch MainActivity")
    }
    //</editor-fold>

    //<editor-fold desc="Тасовка фрагментов">
    private fun managingFragment(fragmentTag: String) {
        val fragment = supportFragmentManager.findFragmentByTag(fragmentTag)
        when (fragmentTag) {
            VkListPostsFragment.FragmentTAG -> if (fragment != null) showFragment(fragment) else addFragment(VkListPostsFragment(), fragmentTag)
            VkListGroupsFragment.FragmentTAG -> if (fragment != null) showFragment(fragment) else addFragment(VkListGroupsFragment(), fragmentTag)
            ChatFragment.FragmentTAG -> if (fragment != null) showFragment(fragment) else addFragment(ChatFragment(), fragmentTag)
        }
        mainPresenter.toggleSearchIcon(fragmentTag == VkListGroupsFragment.FragmentTAG)
    }

    private fun addFragment(fragment: Fragment, fragmentTag: String) {
        checkVisibleFragment()
                .add(fragmentContainer.id, fragment, fragmentTag).commit()
        myAppBar.setExpanded(true, false)
    }

    private fun showFragment(fragment: Fragment) {
        checkVisibleFragment()
                .show(fragment).commit()
    }

    @SuppressLint("CommitTransaction")
    private fun checkVisibleFragment(): FragmentTransaction {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(VkListPostsFragment.FragmentTAG)
        if (fragment != null) fragmentTransaction.hide(fragment)
        fragment = supportFragmentManager.findFragmentByTag(VkListGroupsFragment.FragmentTAG)
        if (fragment != null) fragmentTransaction.hide(fragment)
        fragment = supportFragmentManager.findFragmentByTag(ChatFragment.FragmentTAG)
        if (fragment != null) fragmentTransaction.hide(fragment)
        return fragmentTransaction
    }
    //</editor-fold>
}
