package com.thebrodyaga.vkurse.screen.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.thebrodyaga.vkurse.application.App.Companion.trace
import com.thebrodyaga.vkurse.common.FRAGMENT_LIVECYCLE_CALLBACKS
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by Win10
 *         on 17.04.2018.
 */
abstract class DaggerAppCompatActivity : MvpAppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentLifecycle(), true)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector

    //<editor-fold desc="Lifecycle">
    companion object {
        private fun fragmentLifecycleLog(fragment: Fragment?, bundle: Bundle?) {
            Log.d(FRAGMENT_LIVECYCLE_CALLBACKS, "Fragment: ${if (fragment!=null)fragment.javaClass.simpleName +
                    ", Id: "+ System.identityHashCode(fragment) else "null"}"  +
                    ", MethodName: ${trace(Thread.currentThread().stackTrace,"access\$fragmentLifecycleLog")}, " +
                    "Bundle: ${if (bundle != null) bundle.javaClass.simpleName +
                            ", Id: " + System.identityHashCode(bundle) else "null"}")
        }

        private fun fragmentLifecycleLog(fragment: Fragment?) {
            Log.d(FRAGMENT_LIVECYCLE_CALLBACKS, ("Fragment:  ${if (fragment!=null)fragment.javaClass.simpleName +
                    ", Id: "+ System.identityHashCode(fragment) else "null"}"  +
                    ", MethodName: " + trace(Thread.currentThread().stackTrace,"access\$fragmentLifecycleLog")))
        }
    }

    private class FragmentLifecycle : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
            super.onFragmentPreAttached(fm, f, context)
            fragmentLifecycleLog(f)
        }

        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
            super.onFragmentAttached(fm, f, context)
            fragmentLifecycleLog(f)
        }

        override fun onFragmentCreated(fm: FragmentManager?, f: Fragment?, savedInstanceState: Bundle?) {
            super.onFragmentCreated(fm, f, savedInstanceState)
            fragmentLifecycleLog(f,savedInstanceState)

        }

        override fun onFragmentActivityCreated(fm: FragmentManager?, f: Fragment?, savedInstanceState: Bundle?) {
            super.onFragmentActivityCreated(fm, f, savedInstanceState)
            fragmentLifecycleLog(f,savedInstanceState)
        }

        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            fragmentLifecycleLog(f,savedInstanceState)
        }

        override fun onFragmentStarted(fm: FragmentManager?, f: Fragment?) {
            super.onFragmentStarted(fm, f)
            fragmentLifecycleLog(f)
        }

        override fun onFragmentResumed(fm: FragmentManager?, f: Fragment?) {
            super.onFragmentResumed(fm, f)
            fragmentLifecycleLog(f)
        }

        override fun onFragmentPaused(fm: FragmentManager?, f: Fragment?) {
            super.onFragmentPaused(fm, f)
            fragmentLifecycleLog(f)
        }

        override fun onFragmentStopped(fm: FragmentManager?, f: Fragment?) {
            super.onFragmentStopped(fm, f)
            fragmentLifecycleLog(f)
        }

        override fun onFragmentSaveInstanceState(fm: FragmentManager?, f: Fragment?, outState: Bundle?) {
            super.onFragmentSaveInstanceState(fm, f, outState)
            fragmentLifecycleLog(f,outState)
        }

        override fun onFragmentViewDestroyed(fm: FragmentManager?, f: Fragment?) {
            super.onFragmentViewDestroyed(fm, f)
            fragmentLifecycleLog(f)
        }

        override fun onFragmentDestroyed(fm: FragmentManager?, f: Fragment?) {
            super.onFragmentDestroyed(fm, f)
            fragmentLifecycleLog(f)
        }

        override fun onFragmentDetached(fm: FragmentManager?, f: Fragment?) {
            super.onFragmentDetached(fm, f)
            fragmentLifecycleLog(f)
        }
    }
    //</editor-fold>
}