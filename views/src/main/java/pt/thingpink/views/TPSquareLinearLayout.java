package pt.thingpink.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TPSquareLinearLayout extends LinearLayout {

	public TPSquareLinearLayout(Context context) {
		super(context);
	}

	public TPSquareLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TPSquareLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
	}

}