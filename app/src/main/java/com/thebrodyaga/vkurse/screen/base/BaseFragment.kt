package com.thebrodyaga.vkurse.screen.base

import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.thebrodyaga.vkurse.application.App

/**
 * Created by Win10
 *         on 19.04.2018.
 */
abstract class BaseFragment : MvpAppCompatFragment() {
    private var toast: Toast? = null

    override fun onDestroy() {
        super.onDestroy()
        App.getRefWatcher(context)?.watch(this)
    }

    fun showToast(text: String) {
        if (toast != null && toast?.view != null) {
            toast?.setText(text)
        } else toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast?.show()
    }
}