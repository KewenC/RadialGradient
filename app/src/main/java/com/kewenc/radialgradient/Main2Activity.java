package com.kewenc.radialgradient;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.coocent.marquee.MarqueeEntity;
import com.coocent.marquee.MarqueeLoader;
import com.coocent.marquee.MarqueeMainUtil;
import com.coocent.marquee.MarqueeSweepGradientView;
import com.coocent.marquee.MarqueeSystemBarUtil;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private MarqueeSweepGradientView mainSweView;
    private PathView pathView;
//    private MyOrientationEventListener myOrientationEventListener;
//    private AnimView animView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        MarqueeSystemBarUtil.hideStatusBarNavigationBar(this);
        mainSweView = findViewById(R.id.mainSweView);
        mainSweView.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<MarqueeEntity> marqueeLists = MarqueeLoader.getInstance(Main2Activity.this).getData();
                int[] colors = new int[marqueeLists.size()+1];//保证头尾颜色值一样
                for (int i=0;i<colors.length;i++){
                    if (i == colors.length -1){
                        colors[i] = colors[0];
                    } else {
                        colors[i] = Color.parseColor(marqueeLists.get(i).getColor());
                    }
                }
                mainSweView.setBuilder(5,4,colors);
            }
        });
        pathView = findViewById(R.id.pathView);
//        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pathView.getVisibility() == View.VISIBLE){
//                    pathView.setVisibility(View.GONE);
//                } else pathView.setVisibility(View.VISIBLE);
////                animView.changeAnimStatus();
//            }
//        });
//        animView = findViewById(R.id.animView);

//        myOrientationEventListener = new MyOrientationEventListener() {
//            @Override
//            public void onOrientationChanged(int orientation) {
//
//            }
//        };
//        myOrientationEventListener.enable();
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        int tmp = windowManager.getDefaultDisplay().getRotation();
//        Log.d("TAGF","Activity_onConfigurationChanged_tmp="+tmp);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        myOrientationEventListener.disable();
    }
}
