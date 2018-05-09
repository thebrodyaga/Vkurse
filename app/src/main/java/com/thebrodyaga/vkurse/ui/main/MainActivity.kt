package com.thebrodyaga.vkurse.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.widget.SearchView
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
import com.thebrodyaga.vkurse.ui.groupList.VkListGroupsFragment
import com.thebrodyaga.vkurse.ui.main.mvp.MainPresenter
import com.thebrodyaga.vkurse.ui.main.mvp.MainView
import com.thebrodyaga.vkurse.ui.postList.VkListPostsFragment
import com.thebrodyaga.vkurse.ui.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(), MainView, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    @Inject
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter
    private var searchItem: MenuItem? = null
    private var searchView: SearchView? = null
    //  при смене конфигурации toggleSearchIcon() вызывается раньше чем onCreateOptionsMenu()
    private var isSearchItemVisible = false
    private var currentQuery: CharSequence? = null

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
        searchView = searchItem?.actionView as SearchView?
        searchItem?.isVisible = isSearchItemVisible
        Handler().post {
            if (isSearchItemVisible && currentQuery != null) {
                searchView?.clearFocus()
                searchView?.isIconified = false
                searchItem?.expandActionView()
                searchView?.setQuery(currentQuery, false)
            } else searchView?.setQuery("", false)
            searchView?.maxWidth = Int.MAX_VALUE
            searchView?.setOnQueryTextListener(this)
            searchItem?.setOnActionExpandListener(this)
        }
        return true
    }

    //<editor-fold desc="Presenter">
    override fun showListPostsFragment() {
        Log.d(DEBUG_TAG, "showListPostsFragment")
        if (searchItem?.isActionViewExpanded == true) searchItem?.collapseActionView()
        managingFragment(VkListPostsFragment.FragmentTAG)
    }

    override fun showListGroupsFragment() {
        Log.d(DEBUG_TAG, "showListGroupsFragment")
        managingFragment(VkListGroupsFragment.FragmentTAG)
    }

    override fun showChatFragment() {
        Log.d(DEBUG_TAG, "showChatFragment")
        if (searchItem?.isActionViewExpanded == true) searchItem?.collapseActionView()
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

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (searchItem?.isActionViewExpanded == true)
            outState?.putCharSequence(CURRENT_QUERY_REQUEST_FLAG, currentQuery)
        if (!isFinishing) return
        searchView?.setOnQueryTextListener(null)    //при повороте с клавиатурой происходит утечка
        searchItem?.collapseActionView()
        searchItem?.setOnActionExpandListener(null)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        currentQuery = savedInstanceState?.getCharSequence(CURRENT_QUERY_REQUEST_FLAG)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        /*currentQuery = query
        mainPresenter.searchControl(query)*/
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        currentQuery = newText
        mainPresenter.searchControl(newText)
        return false
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.toolbar_search)
            currentQuery = searchView?.query
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.toolbar_search)
            currentQuery = null
        return true
    }

    companion object {
        const val CURRENT_QUERY_REQUEST_FLAG = "currentQueryRequestFlag"
    }
}
