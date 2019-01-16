package com.tianque.lib.router.lifecycle;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class AppLCOCaller {
    private Map<String, AppLCObserver> appLCObservers;

    public AppLCOCaller addLCObserver(AppLCObserver observer) {
        if (appLCObservers == null)
            appLCObservers = new HashMap();
        if (!TextUtils.isEmpty(observer.getTag())) {
            appLCObservers.put(observer.getTag(), observer);
        } else {
            Log.e("tqrouter", "null tag :" + observer.getClass().getName());
        }
        return this;
    }

    public Map<String, AppLCObserver> getAppLCObservers() {
        return appLCObservers;
    }

    public void callOnSetup() {
        for (AppLCObserver appLCObserver : appLCObservers.values()) {
            if (!appLCObserver.isSetuped()) {
                appLCObserver.onSetup(this);
                appLCObserver.setSetuped(true);
            }
        }
    }

    public void callOnCreate(Application application) {
        for (AppLCObserver appLCObserver : appLCObservers.values()) {
            appLCObserver.onCreate(application);
        }
    }

    public void callOnStop() {
        for (AppLCObserver appLCObserver : appLCObservers.values()) {
            appLCObserver.onStop();
        }
    }

    public void loadBundles(String routerFileName) {

    }
}
