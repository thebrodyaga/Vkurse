package com.thebrodyaga.vkurse.ui.base

import android.content.Context
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatFragment
import com.thebrodyaga.vkurse.application.App
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by Win10
 *         on 17.04.2018.
 */
abstract class DaggerSupportFragment : MvpAppCompatFragment(), HasSupportFragmentInjector {
    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return childFragmentInjector
    }

    override fun onDestroy() {
        super.onDestroy()
        App.getRefWatcher(activity)?.watch(this)
    }
}