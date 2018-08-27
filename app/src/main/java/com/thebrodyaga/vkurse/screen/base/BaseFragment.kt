package com.thebrodyaga.vkurse.screen.base

import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast
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
abstract class BaseFragment : MvpAppCompatFragment(), HasSupportFragmentInjector {
    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    private var toast: Toast? = null

    override fun onDestroy() {
        super.onDestroy()
        App.getRefWatcher(context)?.watch(this)
    }

    open fun onBackPressed() {}

    fun showToast(text: String) {
        if (toast != null && toast?.view != null) {
            toast?.setText(text)
        } else toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return childFragmentInjector
    }
}