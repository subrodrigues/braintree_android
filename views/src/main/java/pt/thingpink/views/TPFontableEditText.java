package pt.thingpink.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.Hashtable;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TPFontableEditText extends EditText {


    private final static String BIRTHDATE_REGEX = "^(((([0-2][0-9])|(3[0-1]))\\/((0[0-9])|(1[0-2])))\\/[0-9]{0,4})|((([0-2][0-9])|(3[0-1]))\\/((0[0-9]?)|(1[0-2]?)))|((([0-2][0-9])|(3[0-1]))\\/([0-1]?))|(([0-2][0-9]?)|(3[0-1]?))$";
    private final static String POSTAL_CODE_REGEX = "^(([0-9]{4})(\\-[0-9]{0,3})?)|([0-9]{0,4})$";
    private final static String NIF_REGEX = "^([0-9]{0,9})?$";

    private static final Hashtable<String, SoftReference<Typeface>> fontCache = new Hashtable<String, SoftReference<Typeface>>();
    private boolean cursorBlocked;
    private TextWatcher textWatcher;
    private int regexIndex;

    public TPFontableEditText(Context context) {
        super(context);
    }

    public TPFontableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, pt.thingpink.views.R.styleable.TPFontableEditText);

        boolean isBold = a.getBoolean(pt.thingpink.views.R.styleable.TPFontableEditText_bold, false);
        cursorBlocked = a.getBoolean(pt.thingpink.views.R.styleable.TPFontableEditText_blockCursor, false);
        regexIndex = a.getInt(pt.thingpink.views.R.styleable.TPFontableEditText_inputRegex, -1);

        if (!isInEditMode()) {
            String customFont = a.getString(pt.thingpink.views.R.styleable.TPFontableEditText_font);
            if (!TextUtils.isEmpty(customFont))
                setCustomFont(this, context, customFont, isBold);
        }

        boolean isUnderline = a.getBoolean(pt.thingpink.views.R.styleable.TPFontableEditText_underline, false);
        if (isUnderline) {
            // this.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            SpannableString content = new SpannableString(getText().toString());
            content.setSpan(new UnderlineSpan(), 0, getText().toString().length(), 0);
            setText(content);
        }

        boolean isUppercase = a.getBoolean(pt.thingpink.views.R.styleable.TPFontableEditText_uppercase, false);
        if (isUppercase) {
            setText(getText().toString().toUpperCase(Locale.getDefault()));
        }

        int hintTextColor = a.getColor(pt.thingpink.views.R.styleable.TPFontableEditText_hintTextColor, 0);

        if (hintTextColor != 0)
            setHintTextColor(hintTextColor);

        boolean isHintUppercase = a.getBoolean(pt.thingpink.views.R.styleable.TPFontableEditText_hintUppercase, false);

        if (isHintUppercase)
            setHint(getHint().toString().toUpperCase(Locale.getDefault()));


		/*
         * ViewTreeObserver observer = this.getViewTreeObserver();
		 * observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
		 *
		 * @Override public void onGlobalLayout() { int maxLines = (int)
		 * FontableEditText.this.getHeight() /
		 * FontableEditText.this.getLineHeight();
		 * FontableEditText.this.setMaxLines(maxLines);
		 * FontableEditText.this.getViewTreeObserver
		 * ().removeGlobalOnLayoutListener(this); } });
		 */

        a.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (regexIndex > -1) {
            final Pattern pattern;
            switch (regexIndex) {
                case 0:
                    pattern = Pattern.compile(BIRTHDATE_REGEX);
                    textWatcher = new TextWatcher() {
                        int lastLength = 0;

                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            String text = editable.toString();
                            int length = text.length();
                            Matcher matcher = pattern.matcher(text);
                            if (!matcher.matches()) {
                                if (length > 0)
                                    editable.delete(length - 1, length--);
                            }
                            if ((length == 2 || length == 5) && lastLength <= length) {
                                TPFontableEditText.this.setText(editable.toString() + "/");
                                length++;
                            }
                            lastLength = length;
                        }
                    };
                    break;
                case 1:
                    pattern = Pattern.compile(POSTAL_CODE_REGEX);
                    textWatcher = new TextWatcher() {
                        int lastLength = 0;

                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            String text = editable.toString();
                            int length = text.length();
                            Matcher matcher = pattern.matcher(text);
                            if (!matcher.matches()) {
                                if (length > 0)
                                    editable.delete(length - 1, length--);
                            }
                            if ((length == 4) && lastLength <= length)
                                editable.insert(length++, "-");
                            lastLength = length;
                        }
                    };
                    break;

                case 2:
                    pattern = Pattern.compile(NIF_REGEX);
                    textWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            String text = editable.toString();
                            int length = text.length();
                            Matcher matcher = pattern.matcher(text);
                            if (!matcher.find()) {
                                if (length > 0)
                                    editable.delete(length - 1, length);
                            }
                        }
                    };
                    break;
            }
            this.addTextChangedListener(textWatcher);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (regexIndex > -1) {
            this.removeTextChangedListener(textWatcher);
            textWatcher = null;
        }
        super.onDetachedFromWindow();
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
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        //on selection move cursor to end of text
        if (cursorBlocked)
            setSelection(this.length());
    }
}
