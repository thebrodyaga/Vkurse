package com.thebrodyaga.vkurse.screen.imageSlider

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.model.SlidrListener
import com.r0adkll.slidr.model.SlidrPosition
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.screen.base.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_image_slider.*

class ImageSliderActivity : DaggerAppCompatActivity(), SlidrListener {
    private val evaluator = ArgbEvaluator()
    private var blackColor: Int = Color.BLACK
    private var transparent: Int = Color.TRANSPARENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_slider)
        Slidr.attach(this,
                SlidrConfig.Builder()
                        .position(SlidrPosition.VERTICAL)
                        .primaryColor(ContextCompat.getColor(this, R.color.primaryDarkColor))
                        .secondaryColor(blackColor)
                        .scrimStartAlpha(1.0f)
                        .listener(this)
                        .build())
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "test"
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

    override fun onSlideClosed() {
    }

    override fun onSlideStateChanged(state: Int) {
    }

    override fun onSlideChange(percent: Float) {
        val newColor = evaluator.evaluate(percent, transparent, blackColor) as Int
        frame_layout.setBackgroundColor(newColor)
    }

    override fun onSlideOpened() {
    }
}
