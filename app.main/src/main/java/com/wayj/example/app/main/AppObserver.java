package com.wayj.example.app.main;

import android.app.Application;
import android.util.Log;

import com.tianque.lib.router.lifecycle.AppLCOCaller;
import com.tianque.lib.router.lifecycle.AppLCObserver;

public class AppObserver implements AppLCObserver {
    @Override
    public String getTag() {
        return "main";
    }

    @Override
    public void onSetup(AppLCOCaller lcoCaller) {
        Log.e("asdasd","onSetup");
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
