package com.thebrodyaga.vkurse.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.thebrodyaga.vkurse.application.di.AppComponent
import com.thebrodyaga.vkurse.application.di.DaggerAppComponent
import com.thebrodyaga.vkurse.common.LIVECYCLE_CALLBACKS_TAG
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


/**
 * Created by Win10
 *         on 18.07.2017.
 */

class App : DaggerApplication(), Application.ActivityLifecycleCallbacks {
    private lateinit var refWatcher: RefWatcher

    override fun onCreate() {
        super.onCreate()
        refWatcher = LeakCanary.install(this)
        registerActivityLifecycleCallbacks(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder().application(this).build()
        return appComponent
    }

    //<editor-fold desc="Description">
    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        activityLifecycleLog(activity, bundle)
    }

    override fun onActivityStarted(activity: Activity) {
        activityLifecycleLog(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        activityLifecycleLog(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        activityLifecycleLog(activity)
    }

    override fun onActivityStopped(activity: Activity) {
        activityLifecycleLog(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {
        activityLifecycleLog(activity, bundle)
    }

    override fun onActivityDestroyed(activity: Activity) {
        activityLifecycleLog(activity)
    }

    companion object {
        fun getRefWatcher(context: Context?): RefWatcher? {
            val application = context?.applicationContext as? App
            return application?.refWatcher
        }

        lateinit var appComponent: AppComponent
        private fun activityLifecycleLog(activity: Activity, bundle: Bundle?) {
            Log.d(LIVECYCLE_CALLBACKS_TAG, "Activity: ${activity.javaClass.simpleName}, " +
                    "Id: ${System.identityHashCode(activity)}, " +
                    "MethodName: ${trace(Thread.currentThread().stackTrace)}, " +
                    "Bundle: ${if (bundle != null) bundle.javaClass.simpleName +
                            ", Id: " + System.identityHashCode(bundle) else "null"}")
        }

        private fun activityLifecycleLog(activity: Activity) {
            Log.d(LIVECYCLE_CALLBACKS_TAG, ("Activity: " + activity.javaClass.simpleName +
                    ", Id: " + System.identityHashCode(activity) +
                    ", MethodName: " + trace(Thread.currentThread().stackTrace)))
        }

        private fun trace(e: Array<StackTraceElement>): String {
            var doNext = false
            for (s in e) {
                if (doNext) return s.methodName
                doNext = s.methodName.contains("access\$activityLifecycleLog")
            }
            return "null"
        }
    }
    //</editor-fold>
}

