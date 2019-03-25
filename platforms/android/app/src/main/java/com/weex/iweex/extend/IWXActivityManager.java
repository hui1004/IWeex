package com.weex.iweex.extend;

import android.app.Activity;

import java.lang.ref.WeakReference;

public class IWXActivityManager {
    private static IWXActivityManager sInstance = new IWXActivityManager();

    private WeakReference<Activity> sCurrentActivityWeakRef;


    private IWXActivityManager() {

    }

    public static IWXActivityManager getInstance() {
        return sInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }
}
