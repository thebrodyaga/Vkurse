package com.thebrodyaga.vkurse.ui.activities

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
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.mvp.presenters.MainPresenter
import com.thebrodyaga.vkurse.mvp.presenters.NavigationBarPresenter
import com.thebrodyaga.vkurse.mvp.presenters.ScrollToTopPresenter
import com.thebrodyaga.vkurse.mvp.views.MainView
import com.thebrodyaga.vkurse.mvp.views.NavigationBarView
import com.thebrodyaga.vkurse.mvp.views.ScrollToTopView
import com.thebrodyaga.vkurse.ui.fragments.ChatFragment
import com.thebrodyaga.vkurse.ui.fragments.InstagramListFragment
import com.thebrodyaga.vkurse.ui.fragments.VkListFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : MvpAppCompatActivity(), NavigationBarView, MainView, ScrollToTopView {

    @InjectPresenter
    lateinit var navigationBarPresenter: NavigationBarPresenter
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter
    @InjectPresenter(type = PresenterType.GLOBAL, tag = ScrollToTopPresenter.ScrollToTopPresenterTAG)
    lateinit var scrollToTopPresenter: ScrollToTopPresenter

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
        return true
    }

    override fun showVkFragment() {
        Log.d(DEBUG_TAG, "showVkFragment")
        managingFragment(VkListFragment.FragmentTAG)
    }

    override fun showInstagramFragment() {
        Log.d(DEBUG_TAG, "showInstagramFragment")
        managingFragment(InstagramListFragment.FragmentTAG)
    }

    override fun showChatFragment() {
        Log.d(DEBUG_TAG, "showChatFragment")
        managingFragment(ChatFragment.FragmentTAG)
    }

    override fun showDefaultFragment(fragmentTag: String) {
        Log.d(DEBUG_TAG, "showDefaultFragment")
        managingFragment(fragmentTag)
    }

    override fun startSettingActivity() {
        startActivity(Intent(this, SettingActivity::class.java))
    }


    override fun scrollTop(menuPosition: Int) {    //реализация в фрагремнтах
        Log.d(DEBUG_TAG, "scrollTop MainActivity")
        myAppBar.setExpanded(true)
        return
    }

    //<editor-fold desc="Тасовка фрагментов">
    private fun managingFragment(fragmentTag: String) {
        val fragment = supportFragmentManager.findFragmentByTag(fragmentTag)
        when (fragmentTag) {
            VkListFragment.FragmentTAG -> if (fragment != null) showFragment(fragment) else addFragment(VkListFragment(), fragmentTag)
            InstagramListFragment.FragmentTAG -> if (fragment != null) showFragment(fragment) else addFragment(InstagramListFragment(), fragmentTag)
            ChatFragment.FragmentTAG -> if (fragment != null) showFragment(fragment) else addFragment(ChatFragment(), fragmentTag)
        }
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
        var fragment = supportFragmentManager.findFragmentByTag(VkListFragment.FragmentTAG)
        if (fragment != null) fragmentTransaction.hide(fragment)
        fragment = supportFragmentManager.findFragmentByTag(InstagramListFragment.FragmentTAG)
        if (fragment != null) fragmentTransaction.hide(fragment)
        fragment = supportFragmentManager.findFragmentByTag(ChatFragment.FragmentTAG)
        if (fragment != null) fragmentTransaction.hide(fragment)
        return fragmentTransaction
    }
    //</editor-fold>
}
