package com.kewenc.radialgradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class myView extends View {
		float x = 250;
		float y = 400;
		private Paint mPaint = new Paint();
		private Shader mShader;
		private float mRotate;
		private Matrix mMatrix = new Matrix();
 
		public myView(Context context) {
			super(context);			
			mShader = new SweepGradient(x, y, new int[] {Color.GREEN, Color.RED, Color.BLUE, Color.GREEN}, null);
			mPaint.setShader(mShader);
		}

	public myView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mShader = new SweepGradient(x, y, new int[] {Color.GREEN, Color.RED, Color.BLUE, Color.GREEN}, null);
		mPaint.setShader(mShader);
	}

	public myView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mShader = new SweepGradient(x, y, new int[] {Color.GREEN, Color.RED, Color.BLUE, Color.GREEN}, null);
		mPaint.setShader(mShader);
	}
		
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);
			canvas.drawColor(Color.WHITE);
			mMatrix.setRotate(mRotate, x, y);//旋转mRotate度,圆心为(x,y)
            mShader.setLocalMatrix(mMatrix);
            
            mRotate += 30;//mRotate增加30度
            if (mRotate >= 360) {
                mRotate = 0;
            }
            invalidate();
            
//            canvas.drawCircle(x, y, 180, mPaint);
            canvas.drawRect(0,0,getWidth(),getWidth()/2,mPaint);
		}
	}