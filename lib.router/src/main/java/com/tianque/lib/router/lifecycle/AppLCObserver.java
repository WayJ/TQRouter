package com.tianque.lib.router.lifecycle;

import android.app.Application;

import com.tianque.lib.router.Bundle;

public abstract class AppLCObserver {
    private boolean setuped = false;//tqrouter 的初始化
    private boolean perload = false;//自身模块的初始化，点进模块时候才执行

    public boolean isSetuped() {
        return setuped;
    }

    public void setSetuped(boolean setuped) {
        this.setuped = setuped;
    }

    public boolean isPerload() {
        return perload;
    }

    public void setPerload(boolean perload) {
        this.perload = perload;
    }

    /**
     * 这里必须返回一个tag，可以是模块名，也可以是具体的功能，比如单独初始化bugly的类，tag应设置为bugly。会用这个tag做重复筛选和异常控制
     * @return
     */
    public abstract String getTag();

    /**
     * 可以通过AppLCOCaller.addLCObserver 来在一个模块中添加多个AppLCObserver
     * 建议做法，每一个复杂的组件的初始化单独放一个AppLCObserver，通过tag来区分
     * 例如：BuglyAppObserver,BaiduMapAppObserver。然后通过onSetup方法加入到AppLCOCaller中
     * 对于tag相同的Observer，仅加载最后一个
     *
     * 只会执行一次
     * @param lcoCaller
     */
    public void onSetup(AppLCOCaller lcoCaller){

    }
    public abstract void onCreate(Application application);
    public abstract void onStop();

    public void perload(CallBack callBack){
        callBack.apply();
    }

    public interface CallBack{
        void apply();
    }
}
