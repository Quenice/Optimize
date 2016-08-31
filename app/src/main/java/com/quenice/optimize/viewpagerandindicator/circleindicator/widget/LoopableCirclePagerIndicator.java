package com.quenice.optimize.viewpagerandindicator.circleindicator.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.quenice.optimize.R;
import com.quenice.optimize.viewpagerandindicator.loopviewpager.widget.LoopableViewPager;

/**
 * 可循环的圆点indicator
 * Created by qiubb on 2016/8/19.
 */
public class LoopableCirclePagerIndicator extends View implements ViewPager.OnPageChangeListener {
	private final static String TAG = "LoopableCirclePagerIndicator";
	private ViewPager mViewPager;
	//圆半径
	private int mRadius;
	private int mCircleColor;
	private int mPointColor;
	private int mCircleStorkColor;
	//当前页
	private int mCurrentPage;
	private int mState;
	//圆之间的间隙
	private int mInterval;
	//圆环宽
	private int mCircleStorkWidth;
	//page count
	private int mCount;
	private int mHeight;
	private int mWidth;
	//圆环paint
	private Paint mCircleStorkPaint;
	//圆paint
	private Paint mPointPaint;
	private Paint mCirclePaint;

	public LoopableCirclePagerIndicator(Context context) {
		this(context, null);
	}

	public LoopableCirclePagerIndicator(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LoopableCirclePagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoopableCirclePagerIndicator);
		mRadius = a.getDimensionPixelSize(R.styleable.LoopableCirclePagerIndicator_cpiRadius, 5);
		mInterval = a.getDimensionPixelSize(R.styleable.LoopableCirclePagerIndicator_cpiInterval, 10);
		mCircleColor = a.getColor(R.styleable.LoopableCirclePagerIndicator_cpiCircleColor, ContextCompat.getColor(context, android.R.color.white));
		mPointColor = a.getColor(R.styleable.LoopableCirclePagerIndicator_cpiPointColor, ContextCompat.getColor(context, android.R.color.white));
		mCircleStorkColor = a.getColor(R.styleable.LoopableCirclePagerIndicator_cpiCircleStorkColor, ContextCompat.getColor(context, android.R.color.transparent));
		mCircleStorkWidth = a.getDimensionPixelSize(R.styleable.LoopableCirclePagerIndicator_cpiCircleStorkWidth, 4);
		a.recycle();
		mCircleStorkPaint = new Paint();
		mCircleStorkPaint.setAntiAlias(true);
		mCircleStorkPaint.setStyle(Paint.Style.STROKE);
		mCircleStorkPaint.setStrokeWidth(mCircleStorkWidth);
		mCircleStorkPaint.setColor(mCircleStorkColor);

		mCirclePaint = new Paint();
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setStyle(Paint.Style.FILL);
		mCirclePaint.setColor(mCircleColor);

		mPointPaint = new Paint();
		mPointPaint.setAntiAlias(true);
		mPointPaint.setStyle(Paint.Style.FILL);
		mPointPaint.setColor(mPointColor);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mViewPager == null || mCount <= 0) return;

		int totalCircleWidth = mInterval * (mCount - 1) + mRadius * 2 * mCount;
		float left = (mWidth - totalCircleWidth) / 2.0f;
		float cx;
		float cy = mHeight / 2.0f;
		for (int i = 0; i < mCount; i++) {
			cx = left + (2 * i + 1) * mRadius + i * mInterval;
			canvas.drawCircle(cx, cy, mRadius, mCircleStorkPaint);
			canvas.drawCircle(cx, cy, mRadius - mCircleStorkWidth / 2.0f, mCirclePaint);
		}

		cx = left + (2 * mCurrentPage + 1) * mRadius + mCurrentPage * mInterval;
		canvas.drawCircle(cx, cy, mRadius - mCircleStorkWidth / 2.0f, mPointPaint);
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
	public void setupWithViewPager(LoopableViewPager viewPager) {
		if (viewPager == null || viewPager.getAdapter() == null) return;
		int count;
		if ((count = viewPager.getAdapter().getCount()) == 0) return;
		if (count == 1) {
			mCount = 1;
		} else {
			mCount = count - 2;
		}
		mCurrentPage = 0;
		mViewPager = viewPager;
		mViewPager.removeOnPageChangeListener(this);
		mViewPager.addOnPageChangeListener(this);
		invalidate();
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		mCurrentPage = position == mCount + 1 ? 0 : (position == 0 ? mCount - 1 : position - 1);
		invalidate();
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}
}
