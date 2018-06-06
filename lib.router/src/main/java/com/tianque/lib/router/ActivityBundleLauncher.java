package com.tianque.lib.router;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;

public class ActivityBundleLauncher extends BundleLauncher {

    @Override
    public <T> T createObject(Postcard postcard, Context context, String type) {
        if (type.startsWith("fragment")) {
            if (!(context instanceof Activity)) {
                return null; // context should be an activity which can be add resources asset path
            }
            String packageName = postcard.getBundle().getPackageName();
            if (packageName == null) return null;
            String fname = postcard.getPath();
            if (fname == null || fname.equals("")) {
                fname = packageName + ".MainFragment"; // default
            } else {
                char c = fname.charAt(0);
                if (c == '.') {
                    fname = packageName + fname;
                } else if (c >= 'A' && c <= 'Z') {
                    fname = packageName + "." + fname;
                } else {
                    // TODO: check the full quality fragment class name
                }
            }
            if (type.endsWith("v4")) {
                return (T) Fragment.instantiate(context, fname);
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                return (T) android.app.Fragment.instantiate(context, fname);
            }
        }
        return super.createObject(postcard, context, type);
    }
}
