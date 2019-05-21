package com.wayj.router.example;

import android.app.Application;
import android.content.Context;
import android.util.Log;

//import com.android.lib.router.adapter.replugin.RepluginActivityLauncher;
import com.tianque.lib.router.ActivityBundleLauncher;
import com.tianque.lib.router.TQRouter;
import com.tianque.lib.router.lifecycle.AppLCObserver;

public class MainApplication extends Application {
    private static final String TAG = "MainApplication";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        TQRouter.setUp(this);
        TQRouter.print();

        TQRouter.getAppLCOCaller().callAttachBaseContext(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        TQRouter.setRouterLauncher(new ActivityBundleLauncher());

        TQRouter.getAppLCOCaller().callOnCreate(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        TQRouter.getAppLCOCaller().callOnStop();
    }
}
