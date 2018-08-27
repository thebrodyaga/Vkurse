package com.thebrodyaga.vkurse.screen.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.screen.Screens
import com.thebrodyaga.vkurse.screen.base.BaseActivity
import com.thebrodyaga.vkurse.screen.base.BaseFragment
import com.thebrodyaga.vkurse.screen.fragments.main.MainFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject


class MainActivity : BaseActivity()/*, MainView, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener*/ {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    @Inject
    lateinit var router: Router

    private val currentFragment
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as BaseFragment?


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Replace(Screens.MAIN_FRAGMENT.toString(), null)))
        }
    }

    private val navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.fragmentContainer) {
        override fun showSystemMessage(message: String?) {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }

        override fun createFragment(screenKey: String, data: Any?): Fragment {
            return when (Screens.valueOf(screenKey)) {
                Screens.MAIN_FRAGMENT -> MainFragment()
            }
        }

        override fun exit() {
            finish()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: router.finishChain()
    }
/*
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
        startActivity(Intent(this, ImageSliderActivity::class.java))
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
        if (fragment != null && !fragment.isHidden) fragmentTransaction.hide(fragment)
        fragment = supportFragmentManager.findFragmentByTag(VkListGroupsFragment.FragmentTAG)
        if (fragment != null && !fragment.isHidden) fragmentTransaction.hide(fragment)
        fragment = supportFragmentManager.findFragmentByTag(ChatFragment.FragmentTAG)
        if (fragment != null && !fragment.isHidden) fragmentTransaction.hide(fragment)
        return fragmentTransaction
    }
    //</editor-fold>

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (searchItem?.isActionViewExpanded == true)
            outState?.putCharSequence(CURRENT_QUERY_REQUEST_FLAG, currentQuery)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        currentQuery = savedInstanceState?.getCharSequence(CURRENT_QUERY_REQUEST_FLAG)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        *//*currentQuery = query
        mainPresenter.searchControl(query)*//*
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

    override fun onDestroy() {
        super.onDestroy()
        if (!isFinishing) return
        searchView?.setOnQueryTextListener(null)    //при повороте с клавиатурой происходит утечка
        searchItem?.setOnActionExpandListener(null)
        searchItem?.collapseActionView()
    }

    companion object {
        const val CURRENT_QUERY_REQUEST_FLAG = "currentQueryRequestFlag"
    }*/
}
