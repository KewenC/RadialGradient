package com.kewenc.radialgradient;

import android.content.Context;
import android.util.TypedValue;

public class DisplayUtil {
    public static int dp2px(Context context, int dp){
        try {
            if (context != null){
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
            } else return 0;
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
