package com.kewenc.radialgradient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RadialGradientView radialGradientView = new RadialGradientView(MainActivity.this);
        setContentView(radialGradientView);
    }
}
