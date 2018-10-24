package com.tianque.lib.router;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import java.io.File;
import java.util.HashSet;

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
                return (T) android.support.v4.app.Fragment.instantiate(context, fname);
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                return (T) android.app.Fragment.instantiate(context, fname);
            }
        }
        return super.createObject(postcard, context, type);
    }



    @Override
    public void launchBundle(Bundle bundle, Context context, Postcard postcard) {
        if (!bundle.isLaunchable()) {
            // TODO: Exit app

            return;
        }
        Intent intent=createIntent(context,postcard);
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
//            if (shouldFinishPreviousActivity(activity)) {
//                activity.finish();
//            }
            activity.startActivityForResult(intent, TQRouter.REQUEST_CODE_DEFAULT);
        } else {
            context.startActivity(intent);
        }
    }

    @Override
    public Intent createIntent(Context context, Postcard postcard) {
        Intent intent = new Intent();
        // Intent extras - class
        String activityName = postcard.getBundle().getActivityName();
//        if (!sActivityClasses.contains(activityName)) {
//            if (activityName.endsWith("Activity")) {
//                throw new ActivityNotFoundException("Unable to find explicit activity class " +
//                        "{ " + activityName + " }");
//            }
//
//            String tempActivityName = activityName + "Activity";
//            if (!sActivityClasses.contains(tempActivityName)) {
//                throw new ActivityNotFoundException("Unable to find explicit activity class " +
//                        "{ " + activityName + "(Activity) }");
//            }
//
//            activityName = tempActivityName;
//        }
        intent.setComponent(new ComponentName(context, activityName));

        // Intent extras - params
        String query = postcard.getQuery();
        if (query != null) {
            intent.putExtra(TQRouter.KEY_QUERY, '?'+query);
        }
        if(intent.resolveActivity(context.getPackageManager())!=null){
            return intent;
        }else
            return null;
    }
}
