package com.quenice.optimize.pickerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.quenice.optimize.R;

/**
 * Created by qiubb on 2016/6/20.
 */
public class TestView extends View {

	private Paint mPaint;

	public TestView(Context context) {
		this(context, null);
	}

	public TestView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(getResources().getColor(R.color.colorAccent));
		mPaint.setTextSize(50);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.save();
		canvas.drawColor(Color.GREEN);
		Paint paint=new Paint();
		paint.setTextSize(60);
		paint.setColor(Color.BLUE);
		canvas.drawText("绿色部分为Canvas剪裁前的区域", 20, 80, paint);
		Rect rect=new Rect(20,200,900,1000);
		canvas.clipRect(rect);
		canvas.restore();
//		canvas.drawColor(Color.YELLOW);
		paint.setColor(Color.BLACK);
		canvas.drawText("黄色部分为Canvas剪裁后的区域", 0, 310, paint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		Log.e("TestView", "MeasureSpec.getSize=" + widthSize);
		Log.e("TestView", "getMeasuredWidth=" + getMeasuredWidth());
	}
}
