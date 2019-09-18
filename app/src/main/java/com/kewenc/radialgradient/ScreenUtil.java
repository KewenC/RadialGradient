package com.kewenc.radialgradient;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class ScreenUtil {
    /**
     * 获取屏幕真实的高度
     */
    public static int getRealHeight(Context context){
        WindowManager mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (mWm == null) return 0;
        DisplayMetrics dm = new DisplayMetrics();
        mWm.getDefaultDisplay().getMetrics(dm);
        int height;
        Display display = mWm.getDefaultDisplay();
        Point p = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(p);
            height = p.y;
        } else {
            @SuppressWarnings("rawtypes")
            Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
                height = dm.heightPixels;
            } catch (Exception e) {
                display.getSize(p);
                height = p.y;
            }
        }
        return height;
    }
    /**
     * 获取屏幕真实的宽度
     * @return
     */
    public static int getRealWidth(Context context){
        WindowManager mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (mWm == null) return 0;
        DisplayMetrics dm = new DisplayMetrics();
        mWm.getDefaultDisplay().getMetrics(dm);
        int width;
        Display display = mWm.getDefaultDisplay();
        Point p = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(p);
            width = p.x;
        } else {
            @SuppressWarnings("rawtypes")
            Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
                width = dm.widthPixels;
            } catch (Exception e) {
                display.getSize(p);
                width = p.x;
            }
        }
        return width;
    }
}
