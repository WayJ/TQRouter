package com.tianque.lib.router.post;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

public class AvoidOnResultFragmentV4 extends Fragment {
    private SparseArray<OnPostResultListener> onPostResultListenerArray=new SparseArray<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public void startWithResult(Intent intent, OnPostResultListener callback) {
        synchronized (onPostResultListenerArray) {
            int i = onPostResultListenerArray.size();
            callback.setResultCode(i);
            onPostResultListenerArray.put(i,callback);
            startActivityForResult(intent, callback.getResultCode());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //rxjava方式的处理

        //callback方式的处理
        OnPostResultListener callback = onPostResultListenerArray.get(requestCode);
        if (callback != null) {
            if(resultCode== Activity.RESULT_OK){
                callback.onPostResultOK(data);
            }else{
                callback.onPostResultCancel(data);
            }
        }
    }
}
