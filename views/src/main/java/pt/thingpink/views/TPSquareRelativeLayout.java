package pt.thingpink.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TPSquareRelativeLayout extends LinearLayout {

	public TPSquareRelativeLayout(Context context) {
		super(context);
	}

	public TPSquareRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TPSquareRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredHeight(), getMeasuredHeight());
	}

}