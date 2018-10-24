package com.tianque.lib.router.post;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class PostResultFragmentHelper {
    private static final String TAG = "AvoidOnResult";

    public static void startPostResult(Intent intent, PostRequest postRequest) {
        if (postRequest.getFragmentManager() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            AvoidOnResultFragment postFragment = findAvoidOnResultFragment(postRequest.getFragmentManager());
            if (postFragment == null) {
                postFragment = new AvoidOnResultFragment();
                postRequest.getFragmentManager()
                        .beginTransaction()
                        .add(postFragment, TAG)
                        .commitAllowingStateLoss();
                postRequest.getFragmentManager().executePendingTransactions();
            }
            postFragment.startWithResult(intent,postRequest.getOnPostResultListener());
        } else {
            AvoidOnResultFragmentV4 postFragment = findAvoidOnResultFragmentV4(postRequest.getV4FragmentManager());
            if (postFragment == null) {
                postFragment = new AvoidOnResultFragmentV4();
                postRequest.getV4FragmentManager()
                        .beginTransaction()
                        .add(postFragment, TAG)
                        .commitAllowingStateLoss();
                postRequest.getV4FragmentManager().executePendingTransactions();
            }
            postFragment.startWithResult(intent,postRequest.getOnPostResultListener());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private static AvoidOnResultFragment findAvoidOnResultFragment(android.app.FragmentManager fragmentManager) {
        return (AvoidOnResultFragment) fragmentManager.findFragmentByTag(TAG);
    }


    private static AvoidOnResultFragmentV4 findAvoidOnResultFragmentV4(android.support.v4.app.FragmentManager fragmentManager) {
        return (AvoidOnResultFragmentV4) fragmentManager.findFragmentByTag(TAG);
    }

}
