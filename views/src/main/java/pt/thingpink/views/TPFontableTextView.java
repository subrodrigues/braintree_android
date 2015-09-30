package pt.thingpink.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.Hashtable;
import java.util.Locale;

import helpers.TPAutofitHelper;

public class TPFontableTextView extends TextView implements TPAutofitHelper.OnTextSizeChangeListener{

	private static final Hashtable<String, SoftReference<Typeface>> fontCache = new Hashtable<String, SoftReference<Typeface>>();
    private boolean isSquare = false;
    private boolean sizeToFit = false;
    private TPAutofitHelper mHelper;

	public TPFontableTextView(Context context) {
		super(context);
	}

	public TPFontableTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TPFontableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TPFontableTextView);

		boolean isBold = a.getBoolean(R.styleable.TPFontableTextView_bold, false);

		if (!isInEditMode()) {
			String customFont = a.getString(R.styleable.TPFontableTextView_font);
			if (!TextUtils.isEmpty(customFont))
				setCustomFont(this, context, customFont, isBold);
		}

		boolean isUnderline = a.getBoolean(R.styleable.TPFontableTextView_underline, false);
		if (isUnderline) {
			// this.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
			SpannableString content = new SpannableString(getText().toString());
			content.setSpan(new UnderlineSpan(), 0, getText().toString().length(), 0);
			setText(content);
		}

		boolean isUppercase = a.getBoolean(R.styleable.TPFontableTextView_uppercase, false);
		if (isUppercase) {
			setText(getText().toString().toUpperCase(Locale.getDefault()));
		}

		String startColor = a.getString(R.styleable.TPFontableTextView_gradientStart);
		String endColor = a.getString(R.styleable.TPFontableTextView_gradientEnd);

		if (!TextUtils.isEmpty(startColor) && !TextUtils.isEmpty(endColor)) {

			int textHeight = getLineHeight();

			try {
				int startColorInt = Color.parseColor(startColor);
				int topMiddleColorInt = Color.parseColor(startColor);
				int bottonMiddleColorInt = Color.parseColor(endColor);
				int endColorInt = Color.parseColor(endColor);

				int[] color = { startColorInt, topMiddleColorInt, bottonMiddleColorInt, endColorInt };
				float[] position = { 0, .5f, .5f, 1 };
				TileMode tile_mode = TileMode.MIRROR; // or TileMode.REPEAT;
				LinearGradient lin_grad = new LinearGradient(0, 0, 0, textHeight, color, position, tile_mode);
				Shader shader_gradient = lin_grad;
				this.getPaint().setShader(shader_gradient);
			} catch (Exception ex) {
				Log.e("ERROR", "TPFontableTextView parseColorError: "
                        + ex.getMessage());
			}
		}

        sizeToFit = a.getBoolean(R.styleable.TPFontableTextView_sizeToFit, false);

        if (sizeToFit)
            init(context, attrs, 0);


        a.recycle();
	}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (isSquare) {

            if (getMeasuredWidth() > getMeasuredHeight())
                setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
            else
                setMeasuredDimension(getMeasuredHeight(), getMeasuredHeight());
        }

        if (sizeToFit) {
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);

            if (widthMode == MeasureSpec.EXACTLY) {
                return;
            }

            Layout layout = getLayout();

            if (layout != null) {

                int width = (int) FloatMath.ceil(getMaxLineWidth(layout))
                        + getCompoundPaddingLeft() + getCompoundPaddingRight();
                int height = getMeasuredHeight();

                if (isSquare) {

                    if (getMeasuredWidth() > getMeasuredHeight()) {
                        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
                    } else {
                        setMeasuredDimension(getMeasuredHeight(), getMeasuredHeight());

                    }
                } else {
                    setMeasuredDimension(width, height);
                    // super.onMeasure(width, height);
                    super.setMeasuredDimension(width, height);
                }
            }
        }

    }

    private float getMaxLineWidth(Layout layout) {
        float max_width = 0.0f;
        int lines = layout.getLineCount();
        for (int i = 0; i < lines; i++) {
            if (layout.getLineWidth(i) > max_width) {
                max_width = layout.getLineWidth(i);
            }
        }
        return max_width;
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

	private static boolean setCustomFont(View view, Context ctx, String asset, boolean isBold) {

		if (TextUtils.isEmpty(asset))
			return false;

		Typeface tf = null;

		try {
			tf = getFont(ctx, asset);
			if (view instanceof TextView) {

				if (isBold)
					((TextView) view).setTypeface(tf, Typeface.BOLD);
				else
					((TextView) view).setTypeface(tf);
			}

		} catch (Exception e) {
			Log.e("ERROR", "Could not get typeface: " + asset, e);
			return false;
		}

		return true;
	}

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mHelper = TPAutofitHelper.create(this, attrs, defStyle).addOnTextSizeChangeListener(this);
    }

    // Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        if (mHelper != null) {
            mHelper.setTextSize(unit, size);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLines(int lines) {
        super.setLines(lines);
        if (mHelper != null) {
            mHelper.setMaxLines(lines);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxLines(int maxLines) {
        super.setMaxLines(maxLines);
        if (mHelper != null) {
            mHelper.setMaxLines(maxLines);
        }
    }

    /**
     * @return the {@link TPAutofitHelper} for this View.
     */
    public TPAutofitHelper getAutofitHelper() {
        return mHelper;
    }

    /**
     * @return whether or not the text will be automatically re-sized to fit its
     *         constraints.
     */
    public boolean isSizeToFit() {
        return mHelper.isEnabled();
    }

    /**
     * Sets the property of this field (sizeToFit), to automatically resize the
     * text to fit its constraints.
     */
    public void setSizeToFit() {
        setSizeToFit(true);
    }

    /**
     * If true, the text will automatically be re-sized to fit its constraints;
     * if false, it will act like a normal TextView.
     *
     * @param sizeToFit
     */
    public void setSizeToFit(boolean sizeToFit) {
        mHelper.setEnabled(sizeToFit);
    }

    /**
     * @return the maximum size (in pixels) of the text in this View.
     */
    public float getMaxTextSize() {
        return mHelper.getMaxTextSize();
    }

    /**
     * Set the maximum text size to the given value, interpreted as
     * "scaled pixel" units. This size is adjusted based on the current density
     * and user font size preference.
     *
     * @param size
     *            The scaled pixel size.
     *
     * @attr ref android.R.styleable#TextView_textSize
     */
    public void setMaxTextSize(float size) {
        mHelper.setMaxTextSize(size);
    }

    /**
     * Set the maximum text size to a given unit and value. See TypedValue for
     * the possible dimension units.
     *
     * @param unit
     *            The desired dimension unit.
     * @param size
     *            The desired size in the given units.
     *
     * @attr ref android.R.styleable#TextView_textSize
     */
    public void setMaxTextSize(int unit, float size) {
        mHelper.setMaxTextSize(unit, size);
    }

    /**
     * @return the minimum size (in pixels) of the text in this View.
     */
    public float getMinTextSize() {
        return mHelper.getMinTextSize();
    }

    /**
     * Set the minimum text size to the given value, interpreted as
     * "scaled pixel" units. This size is adjusted based on the current density
     * and user font size preference.
     *
     * @param minSize
     *            The scaled pixel size.
     *
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    public void setMinTextSize(int minSize) {
        mHelper.setMinTextSize(TypedValue.COMPLEX_UNIT_SP, minSize);
    }

    /**
     * Set the minimum text size to a given unit and value. See TypedValue for
     * the possible dimension units.
     *
     * @param unit
     *            The desired dimension unit.
     * @param minSize
     *            The desired size in the given units.
     *
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    public void setMinTextSize(int unit, float minSize) {
        mHelper.setMinTextSize(unit, minSize);
    }

    /**
     * @return the amount of precision used to calculate the correct text size
     *         to fit within its bounds.
     */
    public float getPrecision() {
        return mHelper.getPrecision();
    }

    /**
     * Set the amount of precision used to calculate the correct text size to
     * fit within its bounds. Lower precision is more precise and takes more
     * time.
     *
     * @param precision
     *            The amount of precision.
     */
    public void setPrecision(float precision) {
        mHelper.setPrecision(precision);
    }

    @Override
    public void onTextSizeChange(float textSize, float oldTextSize) {
        // do nothing
    }
}
