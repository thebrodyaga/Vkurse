package com.thebrodyaga.vkurse.screen.setting

import android.os.Bundle
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrConfig
import com.thebrodyaga.vkurse.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        /*Slidr.attach(this,
                SlidrConfig.Builder()
                        .build())*/
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
