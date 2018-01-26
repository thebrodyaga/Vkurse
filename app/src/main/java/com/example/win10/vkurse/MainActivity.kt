package com.example.win10.vkurse

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.example.win10.vkurse.R.id.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
       /* (bottom_nav.layoutParams as CoordinatorLayout.LayoutParams)
                .behavior = BottomNavigationViewBehavior()*/
        nvView.setNavigationItemSelectedListener(this)
      //  bottom_nav.setOnNavigationItemSelectedListener(this)

      //  supportFragmentManager.beginTransaction().replace(R.id.flContent, BlankFragment()).commit()
        textView.text = "Start: \n-Line2\n-Line3 \n" +
                "-Line2\n" +
                "-Line3\n" +
                "-Line2\n" +
                "-Line3\n" +
                "-Line2\n" +
                "-Line3\n" +
                "-Line2\n" +
                "-Line3\n" +
                "-Line2\n" +
                "-Line3\n" +
                "-Line2\n" +
                "-Line3\n" +
                "-Line2\n" +
                "-Line3\n" +
                "-Line2\n" +
                "-Line3\n" +
                "-Line2\n" +
                "-Line3\n" +
                "-Line2\n" +
                "-Line3\n" +
                "-Line2\n" +
                "-Finish"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
            action_map -> {
                Toast.makeText(this, "map", Toast.LENGTH_LONG).show()
            }
            action_dial -> {
                Toast.makeText(this, "dial", Toast.LENGTH_LONG).show()
            }
            action_mail -> {
                Toast.makeText(this, "mail", Toast.LENGTH_LONG).show()
            }
        }
        item.isChecked = true
        title = item.title
        drawer_layout.closeDrawers()
        return true
    }

}
