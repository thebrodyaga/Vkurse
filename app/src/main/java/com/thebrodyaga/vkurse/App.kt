package com.thebrodyaga.vkurse

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.squareup.leakcanary.LeakCanary
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.common.LIVECYCLE_CALLBACKS_TAG
import com.thebrodyaga.vkurse.di.AppComponent
import com.thebrodyaga.vkurse.di.DaggerAppComponent
import com.thebrodyaga.vkurse.di.modules.ContextModule

/**
 * Created by Win10
 *         on 18.07.2017.
 */

class App : Application(), Application.ActivityLifecycleCallbacks {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .contextModule(ContextModule(this))
                .build()
        LeakCanary.install(this)
        registerActivityLifecycleCallbacks(this)
    }

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
}

