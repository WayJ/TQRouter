package com.tianque.lib.router.post;


public class PostRequest {
    private OnPostResultListener onPostResultListener;
    private android.support.v4.app.FragmentManager v4FragmentManager;
    private android.app.FragmentManager fragmentManager;

    public OnPostResultListener getOnPostResultListener() {
        return onPostResultListener;
    }

    public void setOnPostResultListener(OnPostResultListener onPostResultListener) {
        this.onPostResultListener = onPostResultListener;
    }

    public android.support.v4.app.FragmentManager getV4FragmentManager() {
        return v4FragmentManager;
    }

    public void setV4FragmentManager(android.support.v4.app.FragmentManager v4FragmentManager) {
        this.v4FragmentManager = v4FragmentManager;
    }

    public android.app.FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(android.app.FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
