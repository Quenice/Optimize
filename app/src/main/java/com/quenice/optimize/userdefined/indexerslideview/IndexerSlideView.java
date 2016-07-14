package com.quenice.optimize.userdefined.indexerslideview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.quenice.optimize.R;

import java.util.ArrayList;

/**
 * 滑动选择view
 * Created by qiubb on 2016/5/24.
 */
public class IndexerSlideView extends View {
	private boolean mDataLoaded;
	private ArrayList<String> mData;
	private IndexerSlideViewAdapter mAdapter;
	private int mWidth;
	private int mHeight;
	private float mTextSize;
	private float mSingleHeight;
	private OnSlideListener onSlideListener;

	private Paint mTextPaint;

	public IndexerSlideView(Context context) {
		this(context, null);
	}

	public IndexerSlideView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public IndexerSlideView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());
		mTextPaint = new Paint();
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!mDataLoaded) return;
		mWidth = getWidth();
		mHeight = getHeight();
		int len = mData.size();
		mSingleHeight = mHeight * 1.0f / len;
		for (int i = 0; i < len; i++) {
			String v = mData.get(i);
			float textWidth = mTextPaint.measureText(v);
			float x = (mWidth - textWidth) / 2;
			float y = i * mSingleHeight + (mSingleHeight + mTextSize) / 2;
			canvas.drawText(v, x, y, mTextPaint);
		}
	}

	public void setAdatper(IndexerSlideViewAdapter adatper) {
		mAdapter = adatper;
		if (mAdapter == null) return;
		mData = adatper.getData();
		if (mData == null || mData.size() == 0) return;
		mDataLoaded = true;
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (onSlideListener == null || mData == null || mData.size() == 0 || mSingleHeight == 0)
			return super.onTouchEvent(event);
		int len = mData.size();
		float x = event.getX();
		float y = event.getY();
		int action = event.getAction();
		int position = (int) (y / mSingleHeight);
		String text;
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				onSlideListener.onSlideStart();
				if (position > len - 1 || position < 0) break;
				text = mData.get(position);
				onSlideListener.onSlide(position, text);
				break;
			case MotionEvent.ACTION_UP:
				onSlideListener.onSlideEnd();
				break;
			case MotionEvent.ACTION_MOVE:
				if (position > len - 1 || position < 0) break;
				text = mData.get(position);
				onSlideListener.onSlide(position, text);
				break;
		}
		return true;
	}

	public void setOnSlideListener(OnSlideListener onSlideListener) {
		this.onSlideListener = onSlideListener;
	}

	/**
	 * 滑动监听类
	 */
	public interface OnSlideListener {
		void onSlideStart();

		void onSlide(int position, String text);

		void onSlideEnd();
	}
}
