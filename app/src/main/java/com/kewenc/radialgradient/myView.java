package com.kewenc.radialgradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class myView extends View {

	float effectiveWidth = 12;
	float effectiveRadius = 16;
	float x = 530;
	float y = 400;
	private Paint mPaint = new Paint();
	private Shader mShader;
	private float mRotate;
	private Matrix mMatrix = new Matrix();

	public myView(Context context) {
		super(context);
		init();
	}

	public myView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public myView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mShader = new SweepGradient(x, y, new int[] {Color.RED, Color.BLUE, Color.RED}, null);
		mPaint.setShader(mShader);
	}


	@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
		canvas.save();

		Path path = new Path();
//		path.addRoundRect(new RectF(100, 1400, 500, 1800), 50, 20, Path.Direction.CW);
		path.addRoundRect(new RectF(effectiveWidth,effectiveWidth,getWidth() - effectiveWidth,getHeight()-effectiveWidth),effectiveRadius,effectiveRadius,Path.Direction.CW);
//		canvas.clipRect(16,16,getWidth() - 16,getHeight()-16,Region.Op.DIFFERENCE);
		canvas.clipPath(path,Region.Op.DIFFERENCE);
		//底层背景
		canvas.drawColor(Color.BLACK);
		mMatrix.setRotate(mRotate, x, y);//旋转mRotate度,圆心为(x,y)
		mShader.setLocalMatrix(mMatrix);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			canvas.drawRoundRect(0,0,getWidth(),getHeight(),effectiveRadius,effectiveRadius,mPaint);
		} else {
			canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);
		}

		canvas.restore();

		mRotate += 2;//mRotate增加30度
		if (mRotate >= 360) {
			mRotate = 0;
		}
		invalidate();
//		drawDiff(canvas);
//		drawR(canvas);
        }

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
//		x = getWidth() / 2;
//		y = getHeight() / 2;
//		mShader = new SweepGradient(x, y, new int[] {Color.RED, Color.BLUE, Color.RED}, null);
//		mPaint.setShader(mShader);
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