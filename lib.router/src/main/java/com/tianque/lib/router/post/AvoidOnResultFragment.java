package com.tianque.lib.router.post;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

@SuppressLint("NewApi")
public class AvoidOnResultFragment extends Fragment {
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
