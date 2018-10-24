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
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TQRouter.openUri("main",MainActivity.this);
                startActivity(TQRouter.getIntentOfUri("main",MainActivity.this));
            }
        });
    }
}
