package com.kewenc.radialgradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

/**
 * https://www.li-xyz.com/index.php/archives/2275/
 */
public class PathView extends View implements ViewOrientationEventListener.OnOrientationListener{

    private final Context context;
    private final ViewOrientationEventListener viewOrientationEventListener;
    private Paint paintHelper = new Paint();//TODO
    private Path pathOut = new Path();
    private Path pathIn = new Path();
    private RectF startToTopRectFOut = new RectF();
    private RectF endToTopRectFOut = new RectF();
    private RectF endToBottomRectFOut = new RectF();
    private RectF startToBottomRectFOut = new RectF();

    private RectF startToTopRectFIn = new RectF();
    private RectF endToTopRectFIn = new RectF();
    private RectF endToBottomRectFIn = new RectF();
    private RectF startToBottomRectFIn = new RectF();

    private float TopIn;
    private float TopOut;
    private float BottomIn;
    private float BottomOut;
    private float width;

    private final boolean isCornerBlack;

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paintHelper.setColor(Color.RED);
        paintHelper.setStrokeWidth(5);
        paintHelper.setStyle(Paint.Style.FILL);

        isCornerBlack = true;//四个角落是否为黑色

        width = dp2px(6);

        TopOut = dp2px(60)*2;
        BottomOut = dp2px(20)*2;

        TopIn = dp2px(60)*2;
        BottomIn = dp2px(20)*2;

//        setRectF(startToTopRectFOut,0,0,TopOut);
//        setRectF(endToTopRectFOut,ScreenUtil.getRealWidth(context)-TopOut,0,TopOut);
//        setRectF(endToBottomRectFOut,ScreenUtil.getRealWidth(context)-BottomOut,ScreenUtil.getRealHeight(context)-BottomOut,BottomOut);
//        setRectF(startToBottomRectFOut,0,ScreenUtil.getRealHeight(context)-BottomOut,BottomOut);
//
//        setRectF(startToTopRectFIn,width,width,TopIn);
//        setRectF(endToTopRectFIn,ScreenUtil.getRealWidth(context)-TopIn-width,width,TopIn);
//        setRectF(endToBottomRectFIn,ScreenUtil.getRealWidth(context)-BottomIn-width,ScreenUtil.getRealHeight(context)-BottomIn-width,BottomIn);
//        setRectF(startToBottomRectFIn,width,ScreenUtil.getRealHeight(context)-BottomIn-width,BottomIn);
//
//        pathOut.addArc(startToTopRectFOut,-180,90);
//        pathOut.arcTo(endToTopRectFOut,-90,90);
//        pathOut.arcTo(endToBottomRectFOut,0,90);
//        pathOut.arcTo(startToBottomRectFOut,90,90);
//
//        pathIn.addArc(startToTopRectFIn,-180,90);
//        pathIn.arcTo(endToTopRectFIn,-90,90);
//        pathIn.arcTo(endToBottomRectFIn,0,90);
//        pathIn.arcTo(startToBottomRectFIn,90,90);

        viewOrientationEventListener = new ViewOrientationEventListener(context,this);
        if (viewOrientationEventListener.canDetectOrientation()) {
            viewOrientationEventListener.enable();
        } else {
            viewOrientationEventListener.disable();
        }

//        mOrientationListener = new OrientationEventListener(context, SensorManager.SENSOR_DELAY_NORMAL){
//
//            @Override
//            public void onOrientationChanged(int orientation) {
////                Log.e("TAGF","orientation="+orientation);
//
//                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
//                    return;  //手机平放时，检测不到有效的角度
//                }
//                //只检测是否有四个角度的改变
//                if (orientation > 350 || orientation < 10) { //0度
//                    orientation = 0;
//                } else if (orientation > 80 && orientation < 100) { //90度
//                    orientation = 90;
//                } else if (orientation > 170 && orientation < 190) { //180度
//                    orientation = 180;
//                } else if (orientation > 260 && orientation < 280) { //270度
//                    orientation = 270;
//                } else {
//                    return;
//                }
//
//                Log.e("TAGF ", "onOrientationChanged:" + orientation);
//            }
//        };

    }

    @Override
    public void onOrientationChanged(int orientation) {
        Log.e("TAGF","onOrientationChanged_orientation="+orientation);

    }

    private void setRectF(RectF rectF, float left, float top, float length) {
        rectF.left = left;
        rectF.top = top;
        rectF.right = rectF.left + length;
        rectF.bottom = rectF.top + length;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        Log.e("TAGF","onSizeChanged_"+w+"_"+h+"_"+oldw+"_"+oldh);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int tmp = windowManager.getDefaultDisplay().getRotation();
        Log.e("TAGF","onSizeChanged_方向"+tmp);

        if (tmp == 0){
            paintHelper.setColor(Color.RED);
        } else paintHelper.setColor(Color.BLUE);

        setRectF(startToTopRectFOut,0,0,TopOut);
        setRectF(endToTopRectFOut,getWidth()-TopOut,0,TopOut);
        setRectF(endToBottomRectFOut,getWidth()-BottomOut,getHeight()-BottomOut,BottomOut);
        setRectF(startToBottomRectFOut,0,getHeight()-BottomOut,BottomOut);

        setRectF(startToTopRectFIn,width,width,TopIn);
        setRectF(endToTopRectFIn,getWidth()-TopIn-width,width,TopIn);
        setRectF(endToBottomRectFIn,getWidth()-BottomIn-width,getHeight()-BottomIn-width,BottomIn);
        setRectF(startToBottomRectFIn,width,getHeight()-BottomIn-width,BottomIn);

        pathOut.reset();
        pathOut.addArc(startToTopRectFOut,-180,90);
        pathOut.arcTo(endToTopRectFOut,-90,90);
        pathOut.arcTo(endToBottomRectFOut,0,90);
        pathOut.arcTo(startToBottomRectFOut,90,90);

        pathIn.reset();
        pathIn.addArc(startToTopRectFIn,-180,90);
        pathIn.arcTo(endToTopRectFIn,-90,90);
        pathIn.arcTo(endToBottomRectFIn,0,90);
        pathIn.arcTo(startToBottomRectFIn,90,90);
    }

//    @Override
//    protected void onConfigurationChanged(Configuration newConfig) {
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        int tmp = windowManager.getDefaultDisplay().getRotation();
//        Log.d("TAGF","onConfigurationChanged_方向"+tmp);
//        super.onConfigurationChanged(newConfig);
//    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        Log.d("TAGF","onLayout");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.d("TAGF","onMeasure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        Log.d("TAGF","onDraw");

//        if (isCornerBlack){
//            canvas.save();
//            canvas.clipPath(pathOut,Region.Op.DIFFERENCE);
//            canvas.drawColor(Color.BLACK);
//            canvas.restore();
//        }

        canvas.save();
        canvas.clipPath(pathOut);
        canvas.clipPath(pathIn, Region.Op.DIFFERENCE);
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
