package com.kewenc.radialgradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * https://www.li-xyz.com/index.php/archives/2275/
 */
public class PathView extends View {

    private final Context context;
    private Paint paintHelper = new Paint();//TODO
    private Path path = new Path();
    private RectF startToTopRectF;
    private RectF endToTopRectF;
    private RectF contentRectF2;
    private Paint bgPaint = new Paint();

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paintHelper.setColor(Color.RED);//TODO:
        paintHelper.setStrokeWidth(5);
        paintHelper.setStyle(Paint.Style.FILL);

        bgPaint.setColor(Color.BLUE);

        startToTopRectF = new RectF(0,0,2*dp2px(60),2*dp2px(60));
        endToTopRectF = new RectF(ScreenUtil.getRealWidth(context)-2*dp2px(60),0,ScreenUtil.getRealWidth(context),2*dp2px(60));
//        startToTopRectF = new RectF(200,200,400,400);
        contentRectF2 = new RectF(400, 200, 600, 400);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        path.addArc(startToTopRectF, -225, 225);
//        path.arcTo(contentRectF2, -180, 225, false);
//        path.lineTo(400, 542);

        canvas.save();
        canvas.drawRect(endToTopRectF,bgPaint);
        canvas.restore();

        canvas.save();
        path.addArc(startToTopRectF,-180,90);
        path.arcTo(endToTopRectF,-90,90);
//        path.lineTo(400, 542);

        //路径剪裁
        canvas.clipPath(path);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintHelper);
        canvas.restore();
    }


    /**
     * 转换单位工具
     * @param dp dp
     * @return int
     */
    private int dp2px(float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
}
