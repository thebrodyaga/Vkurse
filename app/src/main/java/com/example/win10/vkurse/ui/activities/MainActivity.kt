package com.example.win10.vkurse.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.win10.vkurse.R
import com.example.win10.vkurse.R.id.*
import com.roughike.bottombar.OnTabReselectListener
import com.roughike.bottombar.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        OnTabSelectListener, OnTabReselectListener {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        nvView.setNavigationItemSelectedListener(this)
        bottomBar.setOnTabSelectListener(this)
        bottomBar.setOnTabReselectListener(this)
        if (supportActionBar != null) {
            val indicator = VectorDrawableCompat.create(resources, R.drawable.ic_menu, theme)
            indicator?.setTint(ResourcesCompat.getColor(resources, R.color.white, theme))
            supportActionBar?.setHomeAsUpIndicator(indicator)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
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
        menuInflater.inflate(R.menu.menu_toolbar, menu)
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

    override fun onTabReSelected(tabId: Int) {
        when (tabId) {
            bottom_map -> {

            }
            bottom_dial -> {

            }
            bottom_mail -> {

            }
        }
    }

    override fun onTabSelected(tabId: Int) {
        when (tabId) {
            bottom_map -> {

            }
            bottom_dial -> {

            }
            bottom_mail -> {

            }
        }
    }


}
