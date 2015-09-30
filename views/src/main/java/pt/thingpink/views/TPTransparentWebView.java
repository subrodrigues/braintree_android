package pt.thingpink.views;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class TPTransparentWebView extends WebView {
	private DirectionsCallback callback;

	public interface DirectionsCallback {
		public void onScroll(int y);
	}

	public TPTransparentWebView(Context context) {
		super(context);
	}

	public TPTransparentWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TPTransparentWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (callback != null) {
			callback.onScroll(t);
		}
	}

	/**
	 * @return the callback
	 */
	public DirectionsCallback getCallback() {
		return callback;
	}

	/**
	 * @param callback
	 *            the callback to set
	 */
	public void setCallback(DirectionsCallback callback) {
		this.callback = callback;
	}
}