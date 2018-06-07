package com.android.lib.router.adapter.replugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.qihoo360.replugin.RePlugin;
import com.tianque.lib.router.ActivityBundleLauncher;
import com.tianque.lib.router.Bundle;
import com.tianque.lib.router.Postcard;
import com.tianque.lib.router.TQRouter;

public class RepluginActivityLauncher extends ActivityBundleLauncher {

    @Override
    public void launchBundle(Bundle bundle, Context context, Postcard postcard) {
        String mainPluginName = bundle.getPackageName();

        {//如果该activity是在本插件内部的，尝试用android自带的start方式，
            // 用了intent.resolveActivity来检测，是否有更好的方式
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(context, postcard.getPath()));
            if(intent.resolveActivity(context.getPackageManager())!=null){
                context.startActivity(intent);
            }
        }

        //检测插件是否安装
        if (RePlugin.isPluginInstalled(mainPluginName)) {
            Intent repluginIntent = RePlugin.createIntent(mainPluginName, postcard.getPath());
            if (TextUtils.isEmpty(postcard.getQuery())) {
                repluginIntent.putExtra(TQRouter.KEY_QUERY, '?' + postcard.getQuery());
            }
            //启动插件
            RePlugin.startActivity(context, repluginIntent);
        } else {
            return;
        }
    }


//    public String getPackageName(Context context){
//        String packageName = ActivityThread.currentOpPackageName();
//        if (packageName == null) {
//            // Package name can be null if the activity thread is running but the app
//            // hasn't bound yet. In this case we fall back to the first package in the
//            // current UID. This works for runtime permissions as permission state is
//            // per UID and permission realted app ops are updated for all UID packages.
//            String[] packageNames = ActivityThread.getPackageManager().getPackagesForUid(
//                    android.os.Process.myUid());
//            if (packageNames != null && packageNames.length > 0) {
//                return packageNames[0];
//            }
//        }
//        return null;
//    }
}
