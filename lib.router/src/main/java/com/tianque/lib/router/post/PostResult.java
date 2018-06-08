package com.tianque.lib.router.post;

import android.content.Intent;

public class PostResult {
    Intent intent;
    int code;

    public PostResult(Intent intent, int code) {
        this.intent = intent;
        this.code = code;
    }

}
