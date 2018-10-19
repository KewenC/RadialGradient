package com.kewenc.radialgradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class MyClip extends FrameLayout {
    private static final String TAG = "xbh";
    private RectF rectF;

    public MyClip(@NonNull Context context) {
        super(context);
    }

    public MyClip(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        setWillNotDraw(false);
    }

    public MyClip(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Path path;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(0,0,getWidth(),getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.save();

        int width = getWidth();
        int height = getHeight();

        int radius = width >> 2;
        int x = width >> 1;
        int y = 5 * radius / 2;

        canvas.clipRect(0,0,width,height);

        path.reset();
        canvas.clipPath(path);
//        path.addCircle(x, y, radius, Path.Direction.CCW);
        path.addArc(rectF,90,360);
        canvas.clipPath(path, Region.Op.REPLACE);
        canvas.drawColor(Color.parseColor("#FFFFFF"));
        canvas.restore();
        canvas.save();
    }
}