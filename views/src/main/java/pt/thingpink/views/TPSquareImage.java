package pt.thingpink.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * This class is responsible
 * 
 * @author Hugo Lopes
 * 
 */
public class TPSquareImage extends ImageView {

    private boolean snapToWidth = false;
    private boolean snapToHeight = false;

	public TPSquareImage(Context context) {
		super(context);
	}

	public TPSquareImage(Context context, AttributeSet attrs) {
		super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TPSquareImage);

        snapToWidth =  a.getBoolean(R.styleable.TPSquareImage_snapToWidth, true);
        snapToHeight =  a.getBoolean(R.styleable.TPSquareImage_snapToHeight, false);

        a.recycle();
	}

	public TPSquareImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(snapToWidth && !snapToHeight)
		    setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());

        else if(snapToHeight)
            setMeasuredDimension(getMeasuredHeight(), getMeasuredHeight());

	}
}