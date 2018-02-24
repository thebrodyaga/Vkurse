package com.thebrodyaga.vkurse.ui.activities

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.thebrodyaga.vkurse.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}
