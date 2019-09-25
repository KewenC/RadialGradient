package com.kewenc.radialgradient;

import android.content.Context;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.WindowManager;

public class ViewOrientationEventListener extends OrientationEventListener {
    private final WindowManager windowManager;
    private OnOrientationListener onOrientationListener;
    private int currentOrientation = Surface.ROTATION_0;

    public ViewOrientationEventListener(Context context){
        super(context, SensorManager.SENSOR_DELAY_NORMAL);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public ViewOrientationEventListener(Context context, OnOrientationListener onOrientationListener) {
        super(context, SensorManager.SENSOR_DELAY_NORMAL);
        this.onOrientationListener = onOrientationListener;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

//    public ViewOrientationEventListener(Context context, int rate) {
//        super(context, rate);
//    }

    @Override
    public void onOrientationChanged(int orientation) {
//        if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
//            return;  //手机平放时，检测不到有效的角度
//        }
//        //只检测是否有四个角度的改变
//        if (orientation > 350 || orientation < 10) { //0度
//            orientation = Surface.ROTATION_0;
//        } else if (orientation > 80 && orientation < 100) { //90度
//            orientation = Surface.ROTATION_90;
//        } else if (orientation > 170 && orientation < 190) { //180度
//            orientation = Surface.ROTATION_180;
//        } else if (orientation > 260 && orientation < 280) { //270度
//            orientation = Surface.ROTATION_270;
//        } else {
//            return;
//        }


//        if (orientation > 350 || orientation < 20) { //0度  90 正竖屏
//            orientation = Surface.ROTATION_0;
//        } else if (orientation > 70 && orientation < 110) { //90度 右横屏
//            orientation = Surface.ROTATION_90;
//        } else if (orientation > 160 && orientation < 200) { //180度 倒竖屏
//            orientation = Surface.ROTATION_180;
//        } else if (orientation > 250 && orientation < 290) { //270度 左横屏
//            orientation = Surface.ROTATION_270;
//        } else return;

//        if (((orientation >= 0) && (orientation <= 45)) || (orientation > 315)){
//            orientation=Surface.ROTATION_0;
//        }else if ((orientation > 45) && (orientation <= 135))  {
//            orientation=Surface.ROTATION_90;
//        }
//        else if ((orientation > 135) && (orientation <= 225)) {
//            orientation=Surface.ROTATION_180;
//        }
//        else if((orientation > 225) && (orientation <= 315)) {
//            orientation=Surface.ROTATION_270;
//        }else {
//            orientation=Surface.ROTATION_0;
//        }


        Log.d("TAGF","角度="+orientation);

        orientation = windowManager.getDefaultDisplay().getRotation();
        Log.d("TAGF","=========================方向="+orientation);

        if (currentOrientation != orientation){
            currentOrientation = orientation;
            if (onOrientationListener != null){
                onOrientationListener.onOrientationChanged(orientation);
            }
        }
    }

    public interface OnOrientationListener{
        void onOrientationChanged(int orientation);
    }

    public void setOnOrientationListener(OnOrientationListener onOrientationListener){
        this.onOrientationListener = onOrientationListener;
    }
}
