package com.kewenc.radialgradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.SweepGradient;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import com.coocent.marquee.MarqueeConstant;

import static com.coocent.marquee.MarqueeConstant.MARQUEE_RADIAN_VALUE_BOTTOM_IN;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_RADIAN_VALUE_BOTTOM_OUT;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_RADIAN_VALUE_TOP_IN;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_RADIAN_VALUE_TOP_OUT;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_SPEED_VALUE;
import static com.coocent.marquee.MarqueeConstant.MARQUEE_WIDTH_VALUE;

/**
 * Created by KewenC on 2019/10/07
 * 裁剪资料参考 ： https://www.li-xyz.com/index.php/archives/2275/
 */
public class PathView extends View implements ViewOrientationEventListener.OnOrientationListener{

    private static final String TAG = "PathView";

    private final Context context;
    private ViewOrientationEventListener viewOrientationEventListener;
    private final int DefaultWidth;
    private final boolean isKnockout;

    private Paint paintHelper = new Paint();
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
    private float effectiveWidth;
    private int[] colors;
    private int baseRotate;
    private float mRotate;

    private final boolean isCornerBlack;
    private SweepGradient mShader;
    private Matrix mMatrix = new Matrix();
    private int currentOrientation;

    private boolean isInward;

    public PathView(Context context){
        super(context);
        Log.e("TAGF","");
        this.context = context;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2){
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);//修复Android 4.2(17)裁剪失效
        }
        baseRotate =  MARQUEE_SPEED_VALUE;
        effectiveWidth = dp2px(MARQUEE_WIDTH_VALUE+ 1);//加1为了兼容低版本
        TopOut = dp2px(MARQUEE_RADIAN_VALUE_TOP_OUT)*2;
        BottomOut = dp2px(MARQUEE_RADIAN_VALUE_BOTTOM_OUT)*2;
        TopIn = dp2px(MARQUEE_RADIAN_VALUE_TOP_IN)*2;
        BottomIn = dp2px(MARQUEE_RADIAN_VALUE_BOTTOM_IN)*2;
        isInward =  false;//宽度伸缩方向是否内侧动，外侧定
        isCornerBlack = true;//四个角落是否为黑色
        isKnockout = true;//内部是否挖空
        DefaultWidth = dp2px(10);//内圆矩形边与View边距离
        colors = new int[]{Color.parseColor("#FF00FF"),Color.parseColor("#FFFF00"),Color.parseColor("#0000FF"),Color.parseColor("#FF00FF")};
        paintHelper.setAntiAlias(true);
        paintHelper.setFilterBitmap(true);

//        viewOrientationEventListener = new ViewOrientationEventListener(context,this);
//        if (viewOrientationEventListener.canDetectOrientation()) {
//            viewOrientationEventListener.enable();
//        } else {
//            viewOrientationEventListener.disable();
//        }
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2){
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);//修复Android 4.2(17)裁剪失效
        }
        baseRotate =  MARQUEE_SPEED_VALUE;
        effectiveWidth = dp2px(MARQUEE_WIDTH_VALUE+ 1);//加1为了兼容低版本
        TopOut = dp2px(MARQUEE_RADIAN_VALUE_TOP_OUT)*2;
        BottomOut = dp2px(MARQUEE_RADIAN_VALUE_BOTTOM_OUT)*2;
        TopIn = dp2px(MARQUEE_RADIAN_VALUE_TOP_IN)*2;
        BottomIn = dp2px(MARQUEE_RADIAN_VALUE_BOTTOM_IN)*2;
//        effectiveRadiusTopIn = dp2px(a.getFloat(com.coocent.marquee.R.styleable.MarqueeSweepGradientView_effectiveRadiusTopIn, MARQUEE_RADIAN_VALUE_TOP_IN));
//        effectiveRadiusTopOut = dp2px(a.getFloat(com.coocent.marquee.R.styleable.MarqueeSweepGradientView_effectiveRadiusTopOut, MARQUEE_RADIAN_VALUE_TOP_OUT));
//        effectiveRadiusBottomIn = dp2px(a.getFloat(com.coocent.marquee.R.styleable.MarqueeSweepGradientView_effectiveRadiusBottomIn, MARQUEE_RADIAN_VALUE_BOTTOM_IN));
//        effectiveRadiusBottomOut = dp2px(a.getFloat(com.coocent.marquee.R.styleable.MarqueeSweepGradientView_effectiveRadiusBottomOut, MARQUEE_RADIAN_VALUE_BOTTOM_OUT));
        isInward =  false;//宽度伸缩方向是否内侧动，外侧定
        isCornerBlack = true;//四个角落是否为黑色
        isKnockout = true;//内部是否挖空
        DefaultWidth = dp2px(10);//内圆矩形边与View边距离
        colors = new int[]{Color.parseColor("#FF00FF"),Color.parseColor("#FFFF00"),Color.parseColor("#0000FF"),Color.parseColor("#FF00FF")};
        paintHelper.setAntiAlias(true);
        paintHelper.setFilterBitmap(true);

//        setRectF(startToTopRectFOut,0,0,TopOut);
//        setRectF(endToTopRectFOut,ScreenUtil.getRealWidth(context)-TopOut,0,TopOut);
//        setRectF(endToBottomRectFOut,ScreenUtil.getRealWidth(context)-BottomOut,ScreenUtil.getRealHeight(context)-BottomOut,BottomOut);
//        setRectF(startToBottomRectFOut,0,ScreenUtil.getRealHeight(context)-BottomOut,BottomOut);
//
//        setRectF(startToTopRectFIn,effectiveWidth,effectiveWidth,TopIn);
//        setRectF(endToTopRectFIn,ScreenUtil.getRealWidth(context)-TopIn-effectiveWidth,effectiveWidth,TopIn);
//        setRectF(endToBottomRectFIn,ScreenUtil.getRealWidth(context)-BottomIn-effectiveWidth,ScreenUtil.getRealHeight(context)-BottomIn-effectiveWidth,BottomIn);
//        setRectF(startToBottomRectFIn,effectiveWidth,ScreenUtil.getRealHeight(context)-BottomIn-effectiveWidth,BottomIn);
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

//        viewOrientationEventListener = new ViewOrientationEventListener(context,this);
//        if (viewOrientationEventListener.canDetectOrientation()) {
//            viewOrientationEventListener.enable();
//        } else {
//            viewOrientationEventListener.disable();
//        }

//        mOrientationListener = new MyOrientationEventListener(context, SensorManager.SENSOR_DELAY_NORMAL){
//
//            @Override
//            public void onOrientationChanged(int orientation) {
////                Log.e("TAGF","orientation="+orientation);
//
//                if (orientation == MyOrientationEventListener.ORIENTATION_UNKNOWN) {
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

    public void setOrientationEnable(){
        viewOrientationEventListener = new ViewOrientationEventListener(context,this);
        if (viewOrientationEventListener.canDetectOrientation()) {
            viewOrientationEventListener.enable();
        } else {
            viewOrientationEventListener.disable();
        }
    }

    public void setOrientationDisable(){
        if (viewOrientationEventListener != null)
            viewOrientationEventListener.disable();
    }

    private void setOrientationMode(int orientation){
        float widthOut,widthIn;
        if (isInward){
            widthOut = 0;
            widthIn = effectiveWidth;
        } else {
            widthOut = DefaultWidth-effectiveWidth;
            widthIn = DefaultWidth;
        }
        switch (orientation){
            case Surface.ROTATION_0:
//                    setRectF(startToTopRectFOut,0,0,TopOut);
//                    setRectF(endToTopRectFOut,getWidth()-TopOut,0,TopOut);
//                    setRectF(endToBottomRectFOut,getWidth()-BottomOut,getHeight()-BottomOut,BottomOut);
//                    setRectF(startToBottomRectFOut,0,getHeight()-BottomOut,BottomOut);
//
//                    setRectF(startToTopRectFIn, effectiveWidth, effectiveWidth,TopIn);
//                    setRectF(endToTopRectFIn,getWidth()-TopIn- effectiveWidth, effectiveWidth,TopIn);
//                    setRectF(endToBottomRectFIn,getWidth()-BottomIn- effectiveWidth,getHeight()-BottomIn- effectiveWidth,BottomIn);
//                    setRectF(startToBottomRectFIn, effectiveWidth,getHeight()-BottomIn- effectiveWidth,BottomIn);
                setRectF(startToTopRectFOut,0+widthOut,0+widthOut,TopOut);
                setRectF(endToTopRectFOut,getWidth()-(TopOut+widthOut),0+widthOut,TopOut);
                setRectF(endToBottomRectFOut,getWidth()-(BottomOut+widthOut),getHeight()-(BottomOut+widthOut),BottomOut);
                setRectF(startToBottomRectFOut,0+widthOut,getHeight()-(BottomOut+widthOut),BottomOut);

                setRectF(startToTopRectFIn, widthIn, widthIn,TopIn);
                setRectF(endToTopRectFIn,getWidth()-TopIn- widthIn, widthIn,TopIn);
                setRectF(endToBottomRectFIn,getWidth()-BottomIn- widthIn,getHeight()-BottomIn- widthIn,BottomIn);
                setRectF(startToBottomRectFIn, widthIn,getHeight()-BottomIn- widthIn,BottomIn);
                break;
            case Surface.ROTATION_90:
                setRectF(startToTopRectFOut,0+widthOut,0+widthOut,TopOut);
                setRectF(endToTopRectFOut,getWidth()-(BottomOut+widthOut),0+widthOut,BottomOut);
                setRectF(endToBottomRectFOut,getWidth()-(BottomOut+widthOut),getHeight()-(BottomOut+widthOut),BottomOut);
                setRectF(startToBottomRectFOut,0+widthOut,getHeight()-(TopOut+widthOut),TopOut);

                setRectF(startToTopRectFIn, widthIn, widthIn,TopIn);
                setRectF(endToTopRectFIn,getWidth()-BottomIn- widthIn, widthIn,BottomIn);
                setRectF(endToBottomRectFIn,getWidth()-BottomIn- widthIn,getHeight()-BottomIn- widthIn,BottomIn);
                setRectF(startToBottomRectFIn, widthIn,getHeight()-TopIn- widthIn,TopIn);
                break;
            case Surface.ROTATION_180:
                setRectF(startToTopRectFOut,0+widthOut,0+widthOut,BottomOut);
                setRectF(endToTopRectFOut,getWidth()-(BottomOut+widthOut),0+widthOut,BottomOut);
                setRectF(endToBottomRectFOut,getWidth()-(TopOut+widthOut),getHeight()-(TopOut+widthOut),TopOut);
                setRectF(startToBottomRectFOut,0+widthOut,getHeight()-(TopOut+widthOut),TopOut);

                setRectF(startToTopRectFIn, widthIn, widthIn,BottomIn);
                setRectF(endToTopRectFIn,getWidth()-BottomIn- widthIn, widthIn,BottomIn);
                setRectF(endToBottomRectFIn,getWidth()-TopIn- widthIn,getHeight()-TopIn- widthIn,TopIn);
                setRectF(startToBottomRectFIn, widthIn,getHeight()-TopIn- widthIn,TopIn);
                break;
            case Surface.ROTATION_270:
                setRectF(startToTopRectFOut,0+widthOut,0+widthOut,BottomOut);
                setRectF(endToTopRectFOut,getWidth()-(TopOut+widthOut),0+widthOut,TopOut);
                setRectF(endToBottomRectFOut,getWidth()-(TopOut+widthOut),getHeight()-(TopOut+widthOut),TopOut);
                setRectF(startToBottomRectFOut,0+widthOut,getHeight()-(BottomOut+widthOut),BottomOut);

                setRectF(startToTopRectFIn, widthIn, widthIn,BottomIn);
                setRectF(endToTopRectFIn,getWidth()-TopIn- widthIn, widthIn,TopIn);
                setRectF(endToBottomRectFIn,getWidth()-TopIn- widthIn,getHeight()-TopIn- widthIn,TopIn);
                setRectF(startToBottomRectFIn, widthIn,getHeight()-BottomIn- widthIn,BottomIn);
                break;
        }

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

    @Override
    public void onOrientationChanged(int orientation) {
        Log.e("TAGF","onOrientationChanged_orientation="+orientation);
        currentOrientation = orientation;
        setOrientationMode(orientation);
        invalidate();
    }

    private void setRectF(RectF rectF, float left, float top, float length) {
        if (length<=0) length = 0.1f;
        rectF.left = left;
        rectF.top = top;
        rectF.right = rectF.left + length;
        rectF.bottom = rectF.top + length;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        initShader();


        Log.e("TAGF","onSizeChanged_"+w+"_"+h+"_"+oldw+"_"+oldh);
//        if (oldw == 0 && oldh == 0){
        Log.e("TAGF","init PathView");
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null){
            onOrientationChanged(windowManager.getDefaultDisplay().getRotation());
        }
//        }

//        if (tmp == 0){
//            paintHelper.setColor(Color.RED);
//        } else paintHelper.setColor(Color.BLUE);
//
//        setRectF(startToTopRectFOut,0,0,TopOut);
//        setRectF(endToTopRectFOut,getWidth()-TopOut,0,TopOut);
//        setRectF(endToBottomRectFOut,getWidth()-BottomOut,getHeight()-BottomOut,BottomOut);
//        setRectF(startToBottomRectFOut,0,getHeight()-BottomOut,BottomOut);
//
//        setRectF(startToTopRectFIn,effectiveWidth,effectiveWidth,TopIn);
//        setRectF(endToTopRectFIn,getWidth()-TopIn-effectiveWidth,effectiveWidth,TopIn);
//        setRectF(endToBottomRectFIn,getWidth()-BottomIn-effectiveWidth,getHeight()-BottomIn-effectiveWidth,BottomIn);
//        setRectF(startToBottomRectFIn,effectiveWidth,getHeight()-BottomIn-effectiveWidth,BottomIn);
//
//        pathOut.reset();
//        pathOut.addArc(startToTopRectFOut,-180,90);
//        pathOut.arcTo(endToTopRectFOut,-90,90);
//        pathOut.arcTo(endToBottomRectFOut,0,90);
//        pathOut.arcTo(startToBottomRectFOut,90,90);
//
//        pathIn.reset();
//        pathIn.addArc(startToTopRectFIn,-180,90);
//        pathIn.arcTo(endToTopRectFIn,-90,90);
//        pathIn.arcTo(endToBottomRectFIn,0,90);
//        pathIn.arcTo(startToBottomRectFIn,90,90);
    }

    private void initShader() {
        mShader = new SweepGradient(getWidth()/2.0f,getHeight()/2.0f,colors,null);
        paintHelper.setShader(mShader);
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

        if (isCornerBlack){
            canvas.save();
            canvas.clipPath(pathOut,Region.Op.DIFFERENCE);
            canvas.drawColor(Color.BLACK);
            canvas.restore();
        }

        canvas.save();
        canvas.clipPath(pathOut);
        if (isKnockout){
            canvas.clipPath(pathIn, Region.Op.DIFFERENCE);
        }
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintHelper);
        canvas.restore();

        mMatrix.setRotate(mRotate, getWidth()/2.0f, getHeight()/2.0f);
        mShader.setLocalMatrix(mMatrix);

        mRotate += baseRotate;
        if (mRotate >= 360) {
            mRotate = 0;
        }
        invalidate();
    }


    /**
     * 转换单位工具
     * @param dp dp
     * @return int
     */
    private int dp2px(float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }

    /**
     * 获取颜色
     * @return colors
     */
    public int[] getColors(){
        return colors;
    }

    /**
     * 构建跑马灯
     * @param speed 速度
     * @param color 颜色
     */
    public void setBuilder(int speed, int[] color){
        baseRotate = speed;
        if (color == null||color.length<=1){
            color = new int[] {Color.WHITE, Color.parseColor(MarqueeConstant.MARQUEE_COLOR_DEFAULT1),
                    Color.parseColor(MarqueeConstant.MARQUEE_COLOR_DEFAULT3), Color.WHITE}; }
        this.colors = color;
        mShader = new SweepGradient(getWidth()/2.0f, getHeight()/2.0f, colors, null);
        paintHelper.setShader(mShader);
        invalidate();
    }

    /**
     * 构建跑马灯
     * @param width 宽度
     * @param speed 速度
     * @param color 颜色
     */
    public void setBuilder(int width, int speed, int[] color){
        effectiveWidth = dp2px(width + 1);
        setOrientationMode(currentOrientation);
        setBuilder(speed, color);
    }

    /**
     * 构建跑马灯
     * @param radius 四弧度都相等
     * @param width 宽度
     * @param speed 速度
     * @param color 颜色
     */
    public void setBuilder(int radius, int width, int speed, int[] color){
        setBuilder(radius, radius, radius, radius, width, speed, color);
    }

    /**
     * 构建跑马灯
     * @param radiusTop 上内和上外弧度相等
     * @param radiusBottom 下内和下外弧度相等
     * @param width 宽度
     * @param speed 速度
     * @param color 颜色
     */
    public void setBuilder(int radiusTop, int radiusBottom, int width, int speed, int[] color){
        setBuilder(radiusTop,radiusBottom,radiusTop,radiusBottom,width,speed, color);
    }

    /**
     * 构建跑马灯
     * @param radiusTopIn 上内弧度
     * @param radiusBottomIn 下内弧度
     * @param radiusTopOut 上外弧度
     * @param radiusBottomOut 下外弧度
     * @param width 宽度
     * @param speed 速度
     */
    public void setBuilder(int radiusTopIn, int radiusBottomIn, int radiusTopOut, int radiusBottomOut, int width, int speed){
        TopIn = dp2px(radiusTopIn)*2;
        BottomIn = dp2px(radiusBottomIn)*2;
        TopOut = dp2px(radiusTopOut)*2;
        BottomOut = dp2px(radiusBottomOut)*2;
        effectiveWidth = dp2px(width + 1);
        setOrientationMode(currentOrientation);

        baseRotate = speed;

        invalidate();
    }

    /**
     * 构建跑马灯
     * @param radiusTopIn 上内弧度
     * @param radiusBottomIn 下内弧度
     * @param radiusTopOut 上外弧度
     * @param radiusBottomOut 下外弧度
     * @param width 宽度
     * @param speed 速度
     * @param color 颜色
     */
    public void setBuilder(int radiusTopIn, int radiusBottomIn, int radiusTopOut, int radiusBottomOut, int width, int speed, int[] color){
        TopIn = dp2px(radiusTopIn)*2;
        BottomIn = dp2px(radiusBottomIn)*2;
        TopOut = dp2px(radiusTopOut)*2;
        BottomOut = dp2px(radiusBottomOut)*2;
        effectiveWidth = dp2px(width + 1);
        setOrientationMode(currentOrientation);

        baseRotate = speed;

        if (color == null||color.length<=1){
            color = new int[] {Color.WHITE, Color.parseColor(MarqueeConstant.MARQUEE_COLOR_DEFAULT1),
                    Color.parseColor(MarqueeConstant.MARQUEE_COLOR_DEFAULT3), Color.WHITE}; }
        this.colors = color;
        mShader = new SweepGradient(getWidth()/2.0f, getHeight()/2.0f, colors, null);
        paintHelper.setShader(mShader);

        invalidate();
    }

    /**
     * 设置速度
     * @param speed 速度
     */
    public void setBaseRotate(int speed){
        this.baseRotate = speed;
        invalidate();
    }

    /**
     * 设置颜色
     * @param color 颜色
     */
    public void setColors(int[] color){
        if (color == null||color.length<=1){
            color = new int[] {Color.WHITE, Color.parseColor(MarqueeConstant.MARQUEE_COLOR_DEFAULT1),
                    Color.parseColor(MarqueeConstant.MARQUEE_COLOR_DEFAULT3), Color.WHITE}; }
        this.colors = color;
        mShader = new SweepGradient(getWidth()/2.0f, getHeight()/2.0f, colors, null);
        paintHelper.setShader(mShader);
        invalidate();
    }

    /**
     * 设置宽度
     * @param width 宽度
     */
    public void setWidth(int width){
        effectiveWidth = dp2px(width + 1);
        setOrientationMode(currentOrientation);
        invalidate();
    }

    /**
     * 设置弧度
     * @param radius 四弧度都相等
     */
    public void setRadius(int radius){
        TopIn = BottomIn = TopOut = BottomOut = dp2px(radius)*2;
        setOrientationMode(currentOrientation);
        invalidate();
    }

    /**
     * 设置弧度
     * @param radiusTop 上内和上外弧度相等
     */
    public void setRadiusTop(int radiusTop){
        TopIn = TopOut = dp2px(radiusTop)*2;
        setOrientationMode(currentOrientation);
        invalidate();
    }

    /**
     * 设置弧度
     * @param radiusBottom 下内和下外弧度相等
     */
    public void setRadiusBottom(int radiusBottom){
        BottomIn = BottomOut = dp2px(radiusBottom)*2;
        setOrientationMode(currentOrientation);
        invalidate();
    }

    /**
     * 设置上内弧度
     * @param radiusTopIn 上内弧度
     */
    public void setRadiusTopIn(int radiusTopIn) {
        TopIn = dp2px(radiusTopIn)*2;
        setOrientationMode(currentOrientation);
        invalidate();
    }

    /**
     * 设置上外弧度
     * @param radiusTopOut 上外弧度
     */
    public void setRadiusTopOut(int radiusTopOut) {
        TopOut = dp2px(radiusTopOut)*2;
        setOrientationMode(currentOrientation);
        invalidate();
    }

    /**
     * 设置下内弧度
     * @param radiusBottomIn 下内弧度
     */
    public void setRadiusBottomIn(int radiusBottomIn) {
        BottomIn = dp2px(radiusBottomIn)*2;
        setOrientationMode(currentOrientation);
        invalidate();
    }

    /**
     * 设置下外弧度
     * @param radiusBottomOut 下外弧度
     */
    public void setRadiusBottomOut(int radiusBottomOut) {
        BottomOut = dp2px(radiusBottomOut)*2;
        setOrientationMode(currentOrientation);
        invalidate();
    }

    /**
     * 获取速度
     * @return baseRotate
     */
    public int getSpeed(){
        return baseRotate;
    }
}
