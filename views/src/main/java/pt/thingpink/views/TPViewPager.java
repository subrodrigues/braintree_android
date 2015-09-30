package pt.thingpink.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class TPViewPager extends ViewPager {

	private boolean isPagingEnabled;
	private float lastX = 0;
	private boolean lockScroll = false;
	private int lastPosition = -1;
	private float mStartDragX;

	public static final int SCROLL_NONE = 0;
	public static final int SCROLL_LEFT = 1;
	public static final int SCROLL_RIGHT = 2;

	public int movingPosition;

	public TPViewPager(Context context) {
		super(context);
		this.isPagingEnabled = true;
		this.movingPosition = SCROLL_NONE;
		setMyScroller();
	}

	public TPViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.isPagingEnabled = true;
		setMyScroller();
	}

	private void setMyScroller() {
		try {
			Class<?> viewpager = ViewPager.class;
			Field scroller = viewpager.getDeclaredField("mScroller");
			scroller.setAccessible(true);
			scroller.set(this, new MyScroller(getContext()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMovingPosition() {
		return this.movingPosition;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (this.isPagingEnabled) {

			float x = event.getX();

			switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					mStartDragX = x;
					break;

				case MotionEvent.ACTION_MOVE:
					if (mStartDragX < x - 100) {// 100 value velocity
						// Left scroll
						// Log.d(Globals.TAG_APPNAME, "Moving Left");
						movingPosition = SCROLL_LEFT;
						return super.onTouchEvent(event);
					} else if (mStartDragX > x + 100) {
						// Right scroll
						// Log.d(Globals.TAG_APPNAME, "Moving Right");
						movingPosition = SCROLL_RIGHT;
						return super.onTouchEvent(event);
					}

					break;

				case MotionEvent.ACTION_UP:
					movingPosition = SCROLL_NONE;
					break;
			}

			return super.onTouchEvent(event);
		} else
			return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {

		float x = event.getX();

		switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN:
				mStartDragX = x;
				break;

			case MotionEvent.ACTION_MOVE:
				if (mStartDragX < x - 100) {// 100 value velocity
					// Left scroll
					// Log.d(Globals.TAG_APPNAME, "Moving Left");
					movingPosition = SCROLL_LEFT;
					return super.onInterceptTouchEvent(event);
				} else if (mStartDragX > x + 100) {
					// Right scroll
					// Log.d(Globals.TAG_APPNAME, "Moving Right");
					movingPosition = SCROLL_RIGHT;
					return super.onInterceptTouchEvent(event);
				}

				break;

			case MotionEvent.ACTION_UP:
				movingPosition = SCROLL_NONE;
				// Log.d(Globals.TAG_APPNAME, "ActionUp");
				break;
		}

		return super.onInterceptTouchEvent(event);
	}

	public void setPagingEnabled(boolean enabled) {
		this.isPagingEnabled = enabled;
	}

	public class MyScroller extends Scroller {
		public MyScroller(Context context) {
			super(context, new AccelerateDecelerateInterpolator());
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy, int duration) {
			super.startScroll(startX, startY, dx, dy, 750 /* 1 secs */);
		}
	}

	public void setLockScroll(boolean lockScroll) {
		this.lockScroll = lockScroll;
	}

	// we some the listner
	// protected OnPageChangeListener listener;

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		super.setOnPageChangeListener(listener);
	}

	//
	// @Override
	// public void setCurrentItem(int item) {
	// // when you pass set current item to 0,
	// // the listener won't be called so we call it on our own
	// boolean invokeMeLater = false;
	//
	// if (super.getCurrentItem() == 0 && item == 0)
	// invokeMeLater = true;
	//
	// super.setCurrentItem(item);
	//
	// if (invokeMeLater)
	// listener.onPageSelected(0);
	// }
}
