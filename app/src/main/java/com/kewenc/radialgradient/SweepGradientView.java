package com.kewenc.radialgradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SweepGradientView extends View {
    private Paint paint;
    private SweepGradient sweepGradient;
    private float mAngle = 0;
    private int[] colors = {Color.RED, Color.BLUE, Color.RED, Color.BLUE, Color.RED};
    private float[] positions = {0f, 0.5f, 1f, 1f};
    private float maxH;
    private RectF rectF;
    private float pding = 4;

    public SweepGradientView(Context context) {
        super(context);
        init();
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        sweepGradient = new SweepGradient(getWidth() / 2, getHeight() / 2, Color.GREEN, Color.BLUE);

        maxH = (float) Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2));
        sweepGradient = new SweepGradient(getWidth() / 2, getHeight() / 2, colors,  null);
        rectF = new RectF(pding, pding , getWidth() - (getWidth() - pding), getHeight() - (getHeight() - pding));
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        int canvasWidth = canvas.getWidth();
//        int canvasHeight = canvas.getHeight();
//        float centerX = canvasWidth / 2f;
//        float centerY = canvasHeight / 2f;
//        float radius = getWidth() / 2;
//        canvas.save();
//        canvas.clipRect(rectF);

        paint.setShader(sweepGradient);
        canvas.rotate(mAngle, getWidth() / 2, getHeight() / 2);
        canvas.drawRect(getWidth() / 2 - maxH, getHeight() / 2 - maxH, getWidth() / 2 + maxH, getHeight() / 2 + maxH, paint);
//        canvas.restore();

        setAngle();
//        setNum();
    }

    private void setNum() {
        for (int i=0;i<positions.length;i++){
            if (positions[i] >= 1){
                positions[i] = 0;
            }
            positions[i] += 0.0005;
        }
        Log.e("TAGF","w"+positions[0]);
        sweepGradient = new SweepGradient(getWidth() / 2, getHeight() / 2, colors,  positions);
        invalidate();
    }

    private void setAngle() {
        mAngle++;
        if (mAngle > 360){
            mAngle = 0;
        }
        invalidate();
    }
}
