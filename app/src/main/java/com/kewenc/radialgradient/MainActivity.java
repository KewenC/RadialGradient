package com.kewenc.radialgradient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        RadialGradientView radialGradientView = new RadialGradientView(MainActivity.this);
//        setContentView(radialGradientView);

//        SweepGradientView sweepGradientView = new SweepGradientView(MainActivity.this);
        setContentView(R.layout.activity_main);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        getWindow().setStatusBarColor(blackColor);
//        getWindow().setNavigationBarColor(blackColor);
    }
}
