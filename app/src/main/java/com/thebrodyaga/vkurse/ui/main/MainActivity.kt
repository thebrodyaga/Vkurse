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
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.ui.base.DaggerAppCompatActivity
import com.thebrodyaga.vkurse.ui.chat.ChatFragment
import com.thebrodyaga.vkurse.ui.list.groups.VkListGroupsFragment
import com.thebrodyaga.vkurse.ui.list.posts.VkListPostsFragment
import com.thebrodyaga.vkurse.ui.main.mvp.presenters.MainPresenter
import com.thebrodyaga.vkurse.ui.main.mvp.views.MainView
import com.thebrodyaga.vkurse.ui.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.support.v7.widget.SearchView as AndroidSearchView


class MainActivity : DaggerAppCompatActivity(), MainView {

    @Inject
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter
    private var searchItem: MenuItem? = null
    //  при смене конфигурации toggleSearchIcon() вызывается раньше чем onCreateOptionsMenu()
    private var isSearchItemVisible = false     //TODO поискать как сделать по-красоте

    @ProvidePresenter
    fun providePresenter(): MainPresenter = mainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        AHBottomNavigationAdapter(this, R.menu.main_bottom_bar).setupWithBottomNavigation(bottomBar)
        bottomBar.accentColor = ContextCompat.getColor(this, R.color.primary)
        bottomBar.setOnTabSelectedListener { position, wasSelected ->
            if (wasSelected) mainPresenter.onBottomBarReClick(position)
            else mainPresenter.onBottomBarClick(position)
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
        searchView?.setOnQueryTextListener(mainPresenter)
        return true
    }

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

    override fun startSettingActivity() {
        startActivity(Intent(this, SettingActivity::class.java))
    }

    override fun toggleSearchIcon(isVisible: Boolean) {
        Log.i(DEBUG_TAG, "toggleSearchIcon isVisible = $isVisible")
        searchItem?.isVisible = isVisible
        isSearchItemVisible = isVisible
    }

    override fun scrollTop() {
        Log.d(DEBUG_TAG, "scrollTop MainActivity")
        myAppBar.setExpanded(true)
    }

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
