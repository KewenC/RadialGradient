package com.kewenc.radialgradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class RadialGradientView extends View {

	float effectiveWidth = 20;
	float effectiveRadius = 16;
	float x = 530;
	float y = 400;
	private Paint mPaint = new Paint();
	private Shader mShader;
	private float mRotate;
	private Matrix mMatrix = new Matrix();
	private Path path = new Path();
	private RectF contentRectf = new RectF();

	public RadialGradientView(Context context) {
		super(context);
		init(context);
	}

	public RadialGradientView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public RadialGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	/**
	 * 获取屏幕真实的宽度
	 * @param mWm
	 * @param dm
	 * @return
	 */
	private int getRealWidth(WindowManager mWm, DisplayMetrics dm){
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

	private void setWindowSize(Context context){
		WindowManager mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		if (mWm == null) return;
		Display display = mWm.getDefaultDisplay();
		Point p = new Point();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			display.getRealSize(p);
			y = p.y/2.0f;
			x = p.x/2.0f;
		} else {
			@SuppressWarnings("rawtypes")
			Class c;
			try {
				c = Class.forName("android.view.Display");
				@SuppressWarnings("unchecked")
				Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
				method.invoke(display, dm);
				y = dm.heightPixels/2.0f;
				x = dm.widthPixels/2.0f;
			} catch (Exception e) {
				display.getSize(p);
				y = p.y/2.0f;
				x = p.x/2.0f;
			}
		}
	}

	private void init(Context context) {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1){
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);//修复Android 4.2(17)裁剪失效
		}
		setWindowSize(context);
//		effectiveRadius = DisplayUtil.dp2px(context, 32);
		mShader = new SweepGradient(x, y, new int[] {Color.RED, Color.BLUE, Color.RED}, null);
		mPaint.setShader(mShader);
	}



	@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
		canvas.save();
		path.addRoundRect(new RectF(effectiveWidth,effectiveWidth,getWidth() - effectiveWidth,getHeight()-effectiveWidth),effectiveRadius,effectiveRadius,Path.Direction.CW);
		canvas.clipPath(path,Region.Op.DIFFERENCE);
		//底层背景
//		canvas.drawColor(Color.BLACK);
		mMatrix.setRotate(mRotate, x, y);//旋转mRotate度,圆心为(x,y)
		mShader.setLocalMatrix(mMatrix);
		canvas.drawRoundRect(contentRectf,effectiveRadius,effectiveRadius,mPaint);

		canvas.restore();

		mRotate += 2;//mRotate增加30度
		if (mRotate >= 360) {
			mRotate = 0;
		}
		invalidate();
        }

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
//		x = getWidth() / 2;
//		y = getHeight() / 2;
//		mShader = new SweepGradient(x, y, new int[] {Color.RED, Color.BLUE, Color.RED}, null);
//		mPaint.setShader(mShader);
		contentRectf.left = 0;
		contentRectf.top = 0;
		contentRectf.right = getWidth();
		contentRectf.bottom = getHeight();
	}

	private void drawDiff(Canvas canvas) {
		canvas.save();
		canvas.translate(10, 10);
		canvas.clipRect(0, 0, 100, 100);
		canvas.clipRect(30, 30, 70, 70, Region.Op.DIFFERENCE);
		drawBg(canvas);
		canvas.restore();
	}

	private void drawBg(Canvas canvas) {
		canvas.clipRect(0, 0, 100, 100);

		canvas.drawColor(Color.WHITE);

		mPaint.setColor(Color.RED);
		canvas.drawLine(0, 0, 100, 100, mPaint);

		mPaint.setColor(Color.GREEN);
		canvas.drawCircle(30, 70, 30, mPaint);

		mPaint.setColor(Color.BLUE);
		canvas.drawText("Clipping", 50, 50, mPaint);
	}

	private void drawR(Canvas canvas){
		canvas.save();
		canvas.clipRect(30,30,getWidth() - 30,getHeight()-30,Region.Op.DIFFERENCE);

		canvas.drawColor(Color.BLUE);
		mPaint.setColor(Color.RED);
		canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);
		canvas.restore();
	}
}