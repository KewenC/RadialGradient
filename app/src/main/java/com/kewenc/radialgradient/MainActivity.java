package com.kewenc.radialgradient;

import android.graphics.Outline;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        SweepGradientView sgv = findViewById(R.id.sgv);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.setClipToOutline(true);
                        outline.setRoundRect(0,0, view.getWidth(),view.getHeight(),30);
                    }
                }
            };
            sgv.setOutlineProvider(viewOutlineProvider);
        }
//        TextView tv_rect = findViewById(R.id.tv_rect);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
//                @Override
//                public void getOutline(View view, Outline outline) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        view.setClipToOutline(true);
//                        outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),30);
//                    }
//                }
//            };
//            tv_rect.setOutlineProvider(viewOutlineProvider);
//        }
        RelativeLayout contentLayout = findViewById(R.id.contentLayout);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.setClipToOutline(true);
                        outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),30);
                    }
                }
            };
            contentLayout.setOutlineProvider(viewOutlineProvider);
        }
    }
}
