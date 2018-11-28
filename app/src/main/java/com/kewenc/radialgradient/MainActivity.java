package com.kewenc.radialgradient;

import android.graphics.Color;
import android.graphics.Outline;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.Window;
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

        //使得布局延伸到状态栏和导航栏区域
        Window window = getWindow();

//        //实现状态栏图标和文字颜色为浅色
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //实现状态栏图标和文字颜色为暗色
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //透明状态栏/导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window.setNavigationBarDividerColor(Color.BLUE);
            }
        }

//        //实现状态栏图标和文字颜色为暗色
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//
//        //实现状态栏图标和文字颜色为浅色
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        getWindow().setStatusBarColor(blackColor);
//        getWindow().setNavigationBarColor(blackColor);

//        SweepGradientView sgv = findViewById(R.id.sgv);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
//                @Override
//                public void getOutline(View view, Outline outline) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        view.setClipToOutline(true);
//                        outline.setRoundRect(0,0, view.getWidth(),view.getHeight(),30);
//                    }
//                }
//            };
//            sgv.setOutlineProvider(viewOutlineProvider);
//        }

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

//        RelativeLayout contentLayout = findViewById(R.id.contentLayout);
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
//            contentLayout.setOutlineProvider(viewOutlineProvider);
//        }
    }
}
