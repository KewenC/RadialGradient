package com.kewenc.radialgradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * 动画滑动View
 */
public class AnimView extends View {
    private final int STATE_INVISIBLE = 0x0001;
    private final int STATE_ANIM = 0x0002;
    private final int STATE_DRAG = 0x0003;
    private int currentState = STATE_INVISIBLE;
    private int point;
    private float[] time = new float[50];
    private float initVal;
    private Paint paint = new Paint();
    private RectF rectF;

    public AnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLUE);
        rectF = new RectF(0,0,dp2px(60),dp2px(60));
        DecelerateInterpolator interpolator = new DecelerateInterpolator();
        float base = 1.0f/time.length;
        for (int i=0;i<time.length;i++){
            time[i] = interpolator.getInterpolation(base*(i+1));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(rectF,paint);
        if (currentState == STATE_ANIM){
            startAnim();
        }
    }

    public void changeAnimStatus(){
        initVal = getWidth() - dp2px(60);//起点到终点距离
        currentState = STATE_ANIM;
        startAnim();
    }

    public void startAnim(){
        if(point < time.length) {
            rectF.left = initVal*time[point];
            rectF.right = rectF.left+dp2px(60);
            point++;
            invalidate();
        } else {
            currentState = STATE_DRAG;
            point = 0;
            invalidate();
        }
    }

    /**
     * 转换单位工具
     * @param dp dp
     * @return int
     */
    private int dp2px(float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }
}
