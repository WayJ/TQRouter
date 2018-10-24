package com.wayj.router.example;

import android.app.Application;

//import com.android.lib.router.adapter.replugin.RepluginActivityLauncher;
import com.tianque.lib.router.ActivityBundleLauncher;
import com.tianque.lib.router.TQRouter;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        TQRouter.setRouterLauncher(new ActivityBundleLauncher());
        TQRouter.setUp(this);

    }



}
