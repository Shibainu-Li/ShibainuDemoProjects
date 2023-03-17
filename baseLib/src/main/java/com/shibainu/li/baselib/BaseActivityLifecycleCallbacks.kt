package com.shibainu.li.baselib

import android.app.Activity
import android.app.Application
import android.os.Bundle

open class BaseActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    private var mActivityCount = 0

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {
        mActivityCount++
        if (mActivityCount == 1) {
            onFrontDesk(activity)
        }
    }

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {
        mActivityCount--
        if (mActivityCount == 0) {
            onBackgroundProcess()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}

    open fun onFrontDesk(activity: Activity){  }

    open fun onBackgroundProcess(){}
}