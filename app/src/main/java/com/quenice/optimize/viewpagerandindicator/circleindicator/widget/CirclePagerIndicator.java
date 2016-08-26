package com.quenice.optimize.viewpagerandindicator.circleindicator.widget;

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
	//是否需要显示平滑滑动过程
	private boolean smoothly = true;

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
		mRadius = dp2px(6);
		mInterval = dp2px(10);
		mCircleStorkWidth = dp2px(4);
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
		if (mViewPager == null || mCount <= 0) return;

		if (mCurrentPage >= mCount) {
			setCurrentItem(mCount - 1);
			return;
		}

		if (mCurrentPage < 0) {
			setCurrentItem(0);
			return;
		}

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
		canvas.drawCircle(cx, cy, mRadius - mCircleStorkWidth / 2, mPointPaint);
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
		Log.e("onPageScrolled", "position=" + position + ",mPositionOffset=" + mPositionOffset);
		if (smoothly || mPositionOffset - 0.01f <= 0.0f || mPositionOffset + 0.01 >= 1.0f)
			invalidate();
	}

	@Override
	public void onPageSelected(int position) {
//		Log.e("onPageSelected", "position=" + position + ", mState=" + mState + ", mPositionOffset=" + mPositionOffset);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
//		Log.e("StateChanged", "state=" + state + "mState=" + mState + ", mPositionOffset=" + mPositionOffset);
		mState = state;
		String stateStr = "other";
		switch (state) {
			case ViewPager.SCROLL_STATE_DRAGGING:
				stateStr = "DRAGGING";
				break;
			case ViewPager.SCROLL_STATE_IDLE:
				stateStr = "IDLE";
//				Log.e("StateChanged", "position=" + mCurrentPage + ",mState=" + stateStr + ", mPositionOffset=" + mPositionOffset);
				break;
			case ViewPager.SCROLL_STATE_SETTLING:
//				Log.e("StateChanged", "position=" + mCurrentPage + ",mState=" + stateStr + ", mPositionOffset=" + mPositionOffset);
				stateStr = "SETTLING";
				break;
		}
//		Log.e(TAG, "state = " + stateStr);

	}

	/**
	 * 设置是否需要显示平滑滑动过程
	 *
	 * @param smoothly
	 */
	public void setSmoothly(boolean smoothly) {
		this.smoothly = smoothly;
	}
}
