package com.example.win10.vkurse.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.example.win10.vkurse.R
import com.example.win10.vkurse.mvp.presenters.MainPresenter
import com.example.win10.vkurse.mvp.presenters.NavigationBarPresenter
import com.example.win10.vkurse.mvp.presenters.ScrollToTopPresenter
import com.example.win10.vkurse.mvp.views.MainView
import com.example.win10.vkurse.mvp.views.NavigationBarView
import com.example.win10.vkurse.mvp.views.ScrollToTopView
import com.example.win10.vkurse.ui.fragments.ChatFragment
import com.example.win10.vkurse.ui.fragments.InstagramListFragment
import com.example.win10.vkurse.ui.fragments.VkListFragment
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
        Log.d("Debug", "showVkFragment")
        replaceFragment(VkListFragment())
    }

    override fun showInstagramFragment() {
        Log.d("Debug", "showInstagramFragment")
        replaceFragment(InstagramListFragment())
    }

    override fun showChatFragment() {
        Log.d("Debug", "showChatFragment")
        replaceFragment(ChatFragment())
    }

    override fun showDefaultFragment(fragment: MvpAppCompatFragment) {
        replaceFragment(fragment)
    }

    override fun startSettingActivity() {
        startActivity(Intent(this, SettingActivity::class.java))
    }


    override fun scrollTop(menuPosition: Int) {    //реализация в фрагремнтах
        Log.d("Debug", "scrollTop MainActivity")
        return
    }

    private fun replaceFragment(fragment: MvpAppCompatFragment) {
        supportFragmentManager.beginTransaction()
                .replace(fragmentContainer.id, fragment).commit()
    }
}
