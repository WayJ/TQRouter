package com.wayj.example.app.main;

import android.app.Application;
import android.util.Log;

import com.tianque.lib.router.TQRouter;
import com.tianque.lib.router.lifecycle.AppLCOCaller;
import com.tianque.lib.router.lifecycle.AppLCObserver;

public class AppObserver extends AppLCObserver {
    @Override
    public String getTag() {
        return "main";
    }

    @Override
    public void onSetup(AppLCOCaller lcoCaller) {
        Log.e("asdasd","onSetup");
        TQRouter.register("router_main.json");
    }

    @Override
    public void onCreate(Application application) {
        Log.e("asdasd","onCreate");
    }

    @Override
    public void onStop() {
        Log.e("asdasd","onStop");
    }
}
