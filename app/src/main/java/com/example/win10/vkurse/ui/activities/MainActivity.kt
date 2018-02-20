package com.example.win10.vkurse.ui.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.GravityCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.example.win10.vkurse.R
import com.example.win10.vkurse.R.id.*
import com.example.win10.vkurse.mvp.presenters.MainPresenter
import com.example.win10.vkurse.mvp.presenters.NavigationBarPresenter
import com.example.win10.vkurse.mvp.presenters.ScrollToTopPresenter
import com.example.win10.vkurse.mvp.presenters.ScrollToTopPresenter.Companion.ScrollToTopPresenterTAG
import com.example.win10.vkurse.mvp.views.MainView
import com.example.win10.vkurse.mvp.views.NavigationBarView
import com.example.win10.vkurse.mvp.views.ScrollToTopView
import com.example.win10.vkurse.ui.fragments.ChatFragment
import com.example.win10.vkurse.ui.fragments.InstagramListFragment
import com.example.win10.vkurse.ui.fragments.VkListFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : MvpAppCompatActivity(), NavigationBarView, MainView,
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        ScrollToTopView {

    @InjectPresenter
    lateinit var navigationBarPresenter: NavigationBarPresenter
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter
    @InjectPresenter(type = PresenterType.GLOBAL, tag = ScrollToTopPresenterTAG)
    lateinit var scrollToTopPresenter: ScrollToTopPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        nvView.setNavigationItemSelectedListener(this)
        bottomBar.setOnNavigationItemSelectedListener { navigationBarPresenter.onBottomBarClick(menuItem = it) }
        bottomBar.setOnNavigationItemReselectedListener { scrollToTopPresenter.onBottomBarReClick(menuItem = it) }
        val indicator = VectorDrawableCompat.create(resources, R.drawable.ic_menu, theme)
        indicator?.setTint(ResourcesCompat.getColor(resources, R.color.white, theme))
        supportActionBar?.setHomeAsUpIndicator(indicator)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
            }
            toolbar_settings -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            nav_first_fragment -> {
                Toast.makeText(this, "first", Toast.LENGTH_LONG).show()
            }
            nav_second_fragment -> {
                Toast.makeText(this, "second", Toast.LENGTH_LONG).show()
            }
            nav_third_fragment -> {
                Toast.makeText(this, "third", Toast.LENGTH_LONG).show()
            }
        }
        item.isChecked = true
        title = item.title
        drawer_layout.closeDrawers()
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

    override fun scrollTop(menuItem: MenuItem) {    //реализация в фрагремнтах
        Log.d("Debug", "scrollTop MainActivity")
        return
    }

    private fun replaceFragment(fragment: MvpAppCompatFragment) {
        supportFragmentManager.beginTransaction()
                .replace(fragmentContainer.id, fragment).commit()
    }
}
