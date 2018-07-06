package com.thebrodyaga.vkurse.screen.group

import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.screen.base.DaggerAppCompatActivity
import com.thebrodyaga.vkurse.screen.group.mvp.VkGroupPresenter
import com.thebrodyaga.vkurse.screen.group.mvp.VkGroupView
import javax.inject.Inject

class VkGroupActivity : DaggerAppCompatActivity(), VkGroupView {

    private var currentGroup = 0

    @Inject
    @InjectPresenter
    lateinit var vkGroupPresenter: VkGroupPresenter

    @ProvidePresenter
    fun providePresenter(): VkGroupPresenter {
        vkGroupPresenter.currentGroup
        return vkGroupPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vk_group)
        currentGroup = intent.getIntExtra(GROUP_ID_FLAG_EXTRA, 0)
    }

    override fun errorFinish() {
        Log.d(DEBUG_TAG, "errorFinish")
        finish()
    }

    companion object {
        const val GROUP_ID_FLAG_EXTRA = "groupIdFlagExtra"
    }
}
