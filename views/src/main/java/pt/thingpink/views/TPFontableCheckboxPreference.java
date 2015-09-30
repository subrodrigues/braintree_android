package pt.thingpink.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.preference.CheckBoxPreference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.Hashtable;

/**
 * Created by José on 22/01/2015.
 */
public class TPFontableCheckboxPreference extends CheckBoxPreference {

    private static final Hashtable<String, SoftReference<Typeface>> fontCache = new Hashtable<String, SoftReference<Typeface>>();
    private String customFont;
    private Context context;
    private float titleTextSize;
    private float summaryTextSize;
    private Drawable checkboxIcon;

    public TPFontableCheckboxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TPFontableCheckboxPreference);

        customFont = a.getString(R.styleable.TPFontableCheckboxPreference_font);
        titleTextSize = a.getDimensionPixelSize(R.styleable.TPFontableCheckboxPreference_titleTextSize, 0);
        summaryTextSize = a.getDimensionPixelSize(R.styleable.TPFontableCheckboxPreference_summaryTextSize, 0);
        checkboxIcon = a.getDrawable(R.styleable.TPFontableCheckboxPreference_checkboxIcon);

        a.recycle();
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        if (!TextUtils.isEmpty(customFont))
            setCustomFont(view, context, customFont);

        if (titleTextSize > 0) {
            TextView titleView = (TextView) view.findViewById(android.R.id.title);
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        }

        if (summaryTextSize > 0) {
            TextView titleView = (TextView) view.findViewById(android.R.id.summary);
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, summaryTextSize);
        }

        if (checkboxIcon != null) {
            CheckBox checkBox = (CheckBox) view.findViewById(android.R.id.checkbox);
            checkBox.setButtonDrawable(checkboxIcon);
        }
    }

    public TPFontableCheckboxPreference(Context context) {
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

            Typeface typeface = Typeface.createFromAsset(c.getAssets(), "fonts/"
                    + name);
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
            TextView descriptionView = (TextView) view.findViewById(android.R.id.summary);

            titleView.setTypeface(tf);
            descriptionView.setTypeface(tf);

        } catch (Exception e) {

            return false;
        }

        return true;
    }
}