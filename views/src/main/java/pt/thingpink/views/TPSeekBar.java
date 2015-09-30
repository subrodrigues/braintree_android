package pt.thingpink.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class TPSeekBar extends SeekBar implements OnSeekBarChangeListener {

	private OnSeekBarChangeListener listener;

	public TPSeekBar(Context context) {
		super(context);
	}

	public TPSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TPSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(h, w, oldh, oldw);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(heightMeasureSpec, widthMeasureSpec);
		setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
	}

	protected void onDraw(Canvas c) {
		c.rotate(-90);
		c.translate(-getHeight(), 0);
		super.onDraw(c);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isEnabled()) {
			return false;
		}

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
			case MotionEvent.ACTION_UP:
				int i = 0;
				i = getMax() - (int) (getMax() * event.getY() / getHeight());
				setProgress(i);
				Log.i("Progress", getProgress() + "");
				onSizeChanged(getWidth(), getHeight(), 0, 0);
				break;

			case MotionEvent.ACTION_CANCEL:
				break;
		}

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (listener != null) {
					listener.onStartTrackingTouch(this);
				}
				break;

			case MotionEvent.ACTION_MOVE:
				if (listener != null) {
					listener.onStartTrackingTouch(this);
				}
				break;

			case MotionEvent.ACTION_UP:
				if (listener != null) {
					listener.onStopTrackingTouch(this);
				}
				invalidate();
				break;

			case MotionEvent.ACTION_CANCEL:
				if (listener != null) {
					listener.onStopTrackingTouch(this);
				}
				break;
		}
		return true;
	}

	@Override
	public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
		this.listener = l;
		super.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if (listener != null)
			listener.onProgressChanged(seekBar, progress, fromUser);
		onSizeChanged(getWidth(), getHeight(), 0, 0);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		if (listener != null)
			listener.onStartTrackingTouch(seekBar);
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		if (listener != null)
			listener.onStopTrackingTouch(seekBar);
	}
}