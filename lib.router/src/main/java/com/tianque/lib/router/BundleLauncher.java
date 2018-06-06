package com.tianque.lib.router;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

public abstract class BundleLauncher {


    public void onCreate(Application app) { }


    public void setUp(Context context) { }


    public void postSetUp() { }


    public boolean resolveBundle(Bundle bundle) {
        if (!preloadBundle(bundle)) return false;

        loadBundle(bundle);
        return true;
    }


    public boolean preloadBundle(Bundle bundle) {
        return true;
    }


    public void loadBundle(Bundle bundle) { }


    public void prelaunchBundle(Bundle bundle) { }


    public void launchBundle(Bundle bundle, Context context, Postcard postcard) {
        if (!bundle.isLaunchable()) {
            // TODO: Exit app

            return;
        }

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (shouldFinishPreviousActivity(activity)) {
                activity.finish();
            }
            activity.startActivityForResult(bundle.getIntent(), TQRouter.REQUEST_CODE_DEFAULT);
        } else {
            context.startActivity(bundle.getIntent());
        }
    }


    public void upgradeBundle(Bundle bundle) {
        // Set flag to tell Small to upgrade bundle while launching application at next time
//        Small.setBundleUpgraded(bundle.getPackageName(), true);
        // TODO: Hotfix
//        bundle.setPatching(true);
//        resolveBundle(bundle);
//        bundle.setPatching(false);
    }


    public <T> T createObject(Postcard postcard, Context context, String type) {
        return null;
    }

    private boolean shouldFinishPreviousActivity(Activity activity) {
        Uri uri = TQRouter.getUri(activity);
        if (uri != null) {
            String fullscreen = uri.getQueryParameter("_fullscreen");
            if (!TextUtils.isEmpty(fullscreen)&&"1".equals(fullscreen)) {
                return true;
            }
        }
        return false;
    }
}
