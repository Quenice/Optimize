package com.quenice.optimize.pickerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by qiubb on 2016/6/17.
 */
public class MyView extends View {
	private int mWidth;
	private int mHeight;
	private Paint mPaint;

	public MyView(Context context) {
		this(context, null);
	}

	public MyView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		String str = "abcghjk";
		float _w = mPaint.measureText(str);

		Paint.FontMetricsInt f = mPaint.getFontMetricsInt();

		float _h = mHeight / 2.0f + (f.bottom - f.top) / 2.0f - f.descent;

		canvas.drawText(str, (mWidth - _w) / 2.0f, _h, mPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredHeight();
	}
}
