package com.tianque.lib.router;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.tianque.lib.router.lifecycle.AppLCObserver;

public class PerloadDialog {
    private Context context;
    private AppLCObserver appLCObserver;
    private Postcard postcard;
    AlertDialog dialog;
    public PerloadDialog(Context context, AppLCObserver observer, Postcard postcard) {
        this.context =context;
        this.appLCObserver= observer;
        this.postcard = postcard;
    }

    public void show(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        dialog =  builder.setTitle("提示")
                .setMessage("载入模块中")
                .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        postcard=null;
                        dismissDialog();
                    }
                })
                .setCancelable(false)
        .create();
        dialog.show();
        appLCObserver.perload(new AppLCObserver.CallBack() {
            @Override
            public void apply() {
                dismissDialog();
                if(postcard!=null) {
                    appLCObserver.setPerload(true);
                    postcard.getBundle().launchFrom(context, postcard);
                }
            }
        });
    }

    public synchronized void dismissDialog(){
        if(dialog!=null){
            try {
                dialog.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
            dialog=null;
        }
    }
}
