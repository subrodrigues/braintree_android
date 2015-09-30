package pt.thingpink.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class TPVideoView extends VideoView {

	private int wVideo;
	private int hVideo;

	public TPVideoView(Context context) {
		super(context);
	}

	public TPVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TPVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setVideoAspect(int w, int h) {
		wVideo = w;
		hVideo = h;
		measure(w, h);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (wVideo != 0 && hVideo != 0)
			setMeasuredDimension(wVideo, hVideo);
	}

}
