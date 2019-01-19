package com.wayj.router.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tianque.lib.router.TQRouter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TQRouter.openUri("main",MainActivity.this);
//                TQRouter.createFragmentV4("main",MainActivity.this);
//                startActivity(TQRouter.getIntentOfUri("main",MainActivity.this));
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TQRouter.openUri("mine",MainActivity.this);
            }
        });

        Class clz = TQRouter.createObject("class","main/AppObserver",null);
        try {
            clz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
