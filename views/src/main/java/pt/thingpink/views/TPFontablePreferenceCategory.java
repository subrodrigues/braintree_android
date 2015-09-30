package pt.thingpink.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.preference.PreferenceCategory;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.Hashtable;

/**
 * Created by José on 22/01/2015.
 */
public class TPFontablePreferenceCategory  extends PreferenceCategory {

    private static final Hashtable<String, SoftReference<Typeface>> fontCache = new Hashtable<String, SoftReference<Typeface>>();
    private String customFont;
    private Context context;

    public TPFontablePreferenceCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TPFontablePreferenceCategory);

        customFont = a.getString(R.styleable.TPFontablePreferenceCategory_font);

        a.recycle();
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        if(!TextUtils.isEmpty(customFont))
            setCustomFont(view, context, customFont);
    }

    public TPFontablePreferenceCategory(Context context) {
        super(context);
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

    private boolean setCustomFont(View view, Context ctx, String asset) {

        if (TextUtils.isEmpty(asset))
            return false;

        Typeface tf = null;

        try {

            tf = getFont(ctx, asset);

            TextView titleView = (TextView) view.findViewById(android.R.id.title);

            titleView.setTypeface(tf);

        } catch (Exception e) {

            return false;
        }

        return true;
    }
}