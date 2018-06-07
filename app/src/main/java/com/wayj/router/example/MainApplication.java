package com.wayj.router.example;

import android.app.Application;

import com.tianque.lib.router.TQRouter;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TQRouter.setRouterLauncher(new RePluginLauncher());
        TQRouter.setUp(application);

    }



}
