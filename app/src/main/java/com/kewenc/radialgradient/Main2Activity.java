package com.kewenc.radialgradient;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.coocent.marquee.MarqueeEntity;
import com.coocent.marquee.MarqueeLoader;
import com.coocent.marquee.MarqueeMainUtil;
import com.coocent.marquee.MarqueeSweepGradientView;
import com.coocent.marquee.MarqueeSystemBarUtil;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private MarqueeSweepGradientView mainSweView;
    private PathView pathView;

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
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pathView.getVisibility() == View.VISIBLE){
                    pathView.setVisibility(View.GONE);
                } else pathView.setVisibility(View.VISIBLE);
            }
        });
    }
}
