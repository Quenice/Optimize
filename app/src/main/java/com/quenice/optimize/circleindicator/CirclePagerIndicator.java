package com.quenice.optimize.circleindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.quenice.optimize.R;

/**
 * 圆点indicator
 * Created by qiubb on 2016/7/14.
 */
public class CirclePagerIndicator extends View implements ViewPager.OnPageChangeListener {
	private final static String TAG = "CirclePagerIndicator";
	private ViewPager mViewPager;
	//圆半径
	private int mRadius;
	//当前页
	private int mCurrentPage;
	private int mState;
	//滑动比例
	private float mPositionOffset;
	//圆之间的间隙
	private int mInterval;
	//圆环宽
	private int mCircleStorkWidth;
	//page count
	private int mCount;
	private int mHeight;
	private int mWidth;
	//圆环paint
	private Paint mCirclePaint;
	//圆paint
	private Paint mPointPaint;
	private int mTimes;

	public CirclePagerIndicator(Context context) {
		this(context, null);
	}

	public CirclePagerIndicator(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CirclePagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		mRadius = dp2px(5);
		mInterval = dp2px(10);
		mCircleStorkWidth = dp2px(1);
		mCirclePaint = new Paint();
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setStyle(Paint.Style.STROKE);
		mCirclePaint.setStrokeWidth(mCircleStorkWidth);
		mCirclePaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));

		mPointPaint = new Paint();
		mPointPaint.setAntiAlias(true);
		mPointPaint.setStyle(Paint.Style.FILL);
		mPointPaint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
	}

	private int dp2px(float dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.e(TAG, canvas.toString());
		if (mViewPager == null || mCount <= 0) return;

		if (mCurrentPage >= mCount) {
			setCurrentItem(mCount - 1);
			return;
		}

		if (mCurrentPage < 0) {
			setCurrentItem(0);
			return;
		}

		Log.e(TAG, "times=" + ++mTimes);
		int totalCircleWidth = mInterval * (mCount - 1) + mRadius * 2 * mCount;
		float left = (mWidth - totalCircleWidth) / 2.0f;
		float cx;
		float cy = mHeight / 2.0f;
		for (int i = 0; i < mCount; i++) {
			cx = left + (2 * i + 1) * mRadius + i * mInterval;
			canvas.drawCircle(cx, cy, mRadius, mCirclePaint);
		}


		cx = left + (2 * mCurrentPage + 1) * mRadius + mCurrentPage * mInterval;
		cx += mPositionOffset * (2 * mRadius + mInterval);
		canvas.drawCircle(cx, cy, mRadius, mPointPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mHeight = getMeasuredHeight();
		mWidth = getMeasuredWidth();
	}

	/**
	 * 结合viewpager
	 *
	 * @param viewPager
	 */
	public void setupWithViewPager(ViewPager viewPager) {
		if (viewPager == null || viewPager.getAdapter() == null) return;
		mCount = viewPager.getAdapter().getCount();
		mViewPager = viewPager;
		mViewPager.addOnPageChangeListener(this);
		invalidate();
	}

	/**
	 * 设置当前页
	 *
	 * @param position
	 */
	public void setCurrentItem(int position) {
		if (mCount <= 0) return;
		mViewPager.setCurrentItem(position % mCount);
		mCurrentPage = position;
		invalidate();
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		mCurrentPage = position;
		mPositionOffset = positionOffset;
		//最右边之后，再往右滑动，不重绘
		if (position == mCount - 1 && !mViewPager.canScrollHorizontally(1)) {
			return;
		}
		//最左边之后，再往左滑动，不重绘
		if (position == 0 && !mViewPager.canScrollHorizontally(-1)) {
			return;
		}
		invalidate();
	}

	@Override
	public void onPageSelected(int position) {
		//滑动稳定下来之后再绘制
		if (mState != ViewPager.SCROLL_STATE_IDLE) return;
		mCurrentPage = position;
		mPositionOffset = 1.0f;
		invalidate();
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		mState = state;
		String stateStr = "other";
		switch (state) {
			case ViewPager.SCROLL_STATE_DRAGGING:
				stateStr = "DRAGGING";
				break;
			case ViewPager.SCROLL_STATE_IDLE:
				stateStr = "IDLE";
				break;
			case ViewPager.SCROLL_STATE_SETTLING:
				stateStr = "SETTLING";
				break;
		}
		Log.e(TAG, "state = " + stateStr);

	}
}
