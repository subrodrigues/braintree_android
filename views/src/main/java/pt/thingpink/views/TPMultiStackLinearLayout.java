package pt.thingpink.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class TPMultiStackLinearLayout extends LinearLayout {

    private List<LinearLayout> mItemsRowList = new ArrayList<>();;

	public TPMultiStackLinearLayout(Context context) { super(context);	}

	public TPMultiStackLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TPMultiStackLinearLayout(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

    public void addItemView(View view){

        if(mItemsRowList.size() == 0){
            insertNewRow();
        }

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        view.measure(point.x, point.y);

        measure(point.x, point.y);

        int width = getWidth();

        int viewWidth = view.getMeasuredWidth() + ((LayoutParams)view.getLayoutParams()).leftMargin + ((LayoutParams)view.getLayoutParams()).rightMargin;

        LinearLayout l = mItemsRowList.get(mItemsRowList.size() - 1);
        l.measure(point.x, point.y);
        int rowWidth = l.getMeasuredWidth();

        if(width > viewWidth + rowWidth) {
            mItemsRowList.get(mItemsRowList.size() - 1).addView(view);
        } else {
            insertNewRow();
            mItemsRowList.get(mItemsRowList.size() - 1).addView(view);
        }
    }

    private void insertNewRow() {
        LinearLayout newLinearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER|Gravity.TOP;
        newLinearLayout.setLayoutParams(params);
        newLinearLayout.setGravity(Gravity.CENTER);
        newLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mItemsRowList.add(newLinearLayout);
        this.addView(newLinearLayout);
    }

    @Override
    public void setOrientation(int orientation){
        super.setOrientation(VERTICAL);
    }
}