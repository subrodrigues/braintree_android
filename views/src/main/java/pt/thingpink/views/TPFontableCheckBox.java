package pt.thingpink.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import java.lang.ref.SoftReference;
import java.util.Hashtable;
import java.util.Locale;

public class TPFontableCheckBox extends CheckBox {

	private static final Hashtable<String, SoftReference<Typeface>> fontCache = new Hashtable<String, SoftReference<Typeface>>();

	public TPFontableCheckBox(Context context) {
		super(context);
	}

	public TPFontableCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TPFontableCheckBox);
		
		if (!isInEditMode()) {
			String customFont = a.getString(R.styleable.TPFontableCheckBox_font);
			if(!TextUtils.isEmpty(customFont))
				setCustomFont(this, context, customFont);
		}

		boolean isUppercase = a.getBoolean(R.styleable.TPFontableCheckBox_uppercase, false);
		if (isUppercase) {
			setText(getText().toString().toUpperCase(Locale.getDefault()));
		}
		
		a.recycle();
	}
	
	public static Typeface getFont(Context c, String name) {
        synchronized (fontCache) {
            if (fontCache.get(name) != null) {
                SoftReference<Typeface> ref = fontCache.get(name);
                if (ref.get() != null) {
                    return ref.get();
                }
            }

            Typeface typeface = Typeface.createFromAsset(
                    c.getAssets(),
                    "fonts/" + name
            );
            fontCache.put(name, new SoftReference<Typeface>(typeface));

            return typeface;
        }
    }
	
	private static boolean setCustomFont(View view, Context ctx, String asset) {
    	
        if (TextUtils.isEmpty(asset))
            return false;
        
        Typeface tf = null;
        
        try {
            tf = getFont(ctx, asset);
            if (view instanceof CheckBox) {
            	((CheckBox) view).setTypeface(tf);
            }
            
        } catch (Exception e) {
            Log.e("ERROR", "Could not get typeface: " + asset, e);
            return false;
        }

        return true;
    }
}
