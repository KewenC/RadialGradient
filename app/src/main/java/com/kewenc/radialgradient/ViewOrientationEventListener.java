package com.kewenc.radialgradient;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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
        super(context, SensorManager.SENSOR_DELAY_UI);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public ViewOrientationEventListener(Context context, OnOrientationListener onOrientationListener) {
        super(context, SensorManager.SENSOR_DELAY_UI);
        this.onOrientationListener = onOrientationListener;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

//    public ViewOrientationEventListener(Context context, int rate) {
//        super(context, rate);
//    }



    @Override
    public void onOrientationChanged(int orientation) {
//        if (orientation == MyOrientationEventListener.ORIENTATION_UNKNOWN) {
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

//    class SensorEventListenerImpl implements SensorEventListener {
//        private static final int _DATA_X = 0;
//        private static final int _DATA_Y = 1;
//        private static final int _DATA_Z = 2;
//
//        public void onSensorChanged(SensorEvent event) {
//            float[] values = event.values;
//            int orientation = ORIENTATION_UNKNOWN;
//            float X = -values[_DATA_X];
//            float Y = -values[_DATA_Y];
//            float Z = -values[_DATA_Z];
//            float magnitude = X*X + Y*Y;
//            // Don't trust the angle if the magnitude is small compared to the y value
//            if (magnitude * 4 >= Z*Z) {
//                float OneEightyOverPi = 57.29577957855f;
//                float angle = (float)Math.atan2(-Y, X) * OneEightyOverPi;
//                orientation = 90 - (int)Math.round(angle);
//                // normalize to 0 - 359 range
//                while (orientation >= 360) {
//                    orientation -= 360;
//                }
//                while (orientation < 0) {
//                    orientation += 360;
//                }
//            }
////            if (mOldListener != null) {
////                mOldListener.onSensorChanged(Sensor.TYPE_ACCELEROMETER, event.values);
////            }
//            if (orientation != mOrientation) {
//                mOrientation = orientation;
//                onOrientationChanged(orientation);
//            }
//        }
//
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//        }
//    }
}
