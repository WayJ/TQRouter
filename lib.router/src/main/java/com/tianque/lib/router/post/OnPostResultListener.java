package com.tianque.lib.router.post;

import android.content.Intent;

public abstract class OnPostResultListener {
    private int resultCode;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public abstract void onPostResultOK(Intent data);


    public abstract void onPostResultCancel(Intent data);
}
