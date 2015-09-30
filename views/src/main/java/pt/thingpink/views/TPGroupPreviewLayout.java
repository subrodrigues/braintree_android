package pt.thingpink.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class TPGroupPreviewLayout extends FrameLayout {

    private boolean square;
    private String font;
    private boolean upperCase;
    private ColorStateList textColor;
    private float textSize;
    private Drawable preview1Src;
    private Drawable preview2Src;
    private Drawable preview3Src;
    private Drawable preview4Src;
    private String text;
    TextView counter;
    ImageView preview1, preview2, preview3, preview4;
    private FrameLayout root;

    public TPGroupPreviewLayout(Context context) {
        super(context);
        init(context);

    }

    public TPGroupPreviewLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs);
        init(context);
    }

    public TPGroupPreviewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TPGroupPreviewLayout);

        font = a.getString(pt.thingpink.views.R.styleable.TPGroupPreviewLayout_font);
        upperCase = a.getBoolean(pt.thingpink.views.R.styleable.TPGroupPreviewLayout_uppercase, false);
        square = a.getBoolean(pt.thingpink.views.R.styleable.TPGroupPreviewLayout_square, false);
        textColor = a.getColorStateList(pt.thingpink.views.R.styleable.TPGroupPreviewLayout_textColor);
        textSize = a.getDimensionPixelSize(R.styleable.TPGroupPreviewLayout_textSize, 0);
        preview1Src = a.getDrawable(pt.thingpink.views.R.styleable.TPGroupPreviewLayout_preview1);
        preview2Src = a.getDrawable(pt.thingpink.views.R.styleable.TPGroupPreviewLayout_preview2);
        preview3Src = a.getDrawable(pt.thingpink.views.R.styleable.TPGroupPreviewLayout_preview3);
        preview4Src = a.getDrawable(pt.thingpink.views.R.styleable.TPGroupPreviewLayout_preview4);
        text = a.getString(R.styleable.TPGroupPreviewLayout_text);

        a.recycle();
    }

    public void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        root = (FrameLayout) inflater.inflate(R.layout.tp_group_preview_layout, this, true);

        preview1 = (ImageView) root.findViewById(R.id.preview1);
        preview2 = (ImageView) root.findViewById(R.id.preview2);
        preview3 = (ImageView) root.findViewById(R.id.preview3);
        preview4 = (ImageView) root.findViewById(R.id.preview4);
        counter = (TextView) root.findViewById(R.id.text);

        if (TextUtils.isEmpty(text) == false) {
            counter.setText(text);
        }
        if (TextUtils.isEmpty(font) == false) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/" + font);
            counter.setTypeface(tf);
        }
        if (textColor != null) {
            counter.setTextColor(textColor);
        }
        if (textSize != 0) {
            counter.setTextSize(textSize);
        }
        counter.setAllCaps(upperCase);
        if (preview1Src != null)
            preview1.setImageDrawable(preview1Src);
        if (preview2Src != null)
            preview2.setImageDrawable(preview2Src);
        if (preview3Src != null)
            preview3.setImageDrawable(preview3Src);
        if (preview4Src != null)
            preview4.setImageDrawable(preview4Src);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (square == true) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setCounterValue(String counterValue) {
        this.counter.setText(String.valueOf(counterValue));
    }

    public void setCounterVisibility(int visibility) {
        this.counter.setVisibility(visibility);
    }

    public void setCounterTextFont(Typeface font) {
        this.counter.setTypeface(font);
    }

    public void setCounterTextSize(float size) {
        this.counter.setTextSize(size);
    }

    public void setCounterTextColor(int color) {
        this.counter.setTextColor(color);
    }

    public void setPreview1Resource(int resource) {
        this.preview1.setImageResource(resource);
    }

    public void setPreview1Bitmap(Bitmap bitmap) {
        this.preview1.setImageBitmap(bitmap);
    }

    public void setPreview1Drawable(Drawable drawable) {
        this.preview1.setImageDrawable(drawable);
    }

    public void setPreview1Background(int color) {
        this.preview1.setBackgroundColor(color);
    }

    public void setPreview2Resource(int resource) {
        this.preview2.setImageResource(resource);
    }

    public void setPreview2Bitmap(Bitmap bitmap) {
        this.preview2.setImageBitmap(bitmap);
    }

    public void setPreview2Drawable(Drawable drawable) {
        this.preview2.setImageDrawable(drawable);
    }

    public void setPreview2Background(int color) {
        this.preview2.setBackgroundColor(color);
    }

    public void setPreview3Resource(int resource) {
        this.preview3.setImageResource(resource);
    }

    public void setPreview3Bitmap(Bitmap bitmap) {
        this.preview3.setImageBitmap(bitmap);
    }

    public void setPreview3Drawable(Drawable drawable) {
        this.preview3.setImageDrawable(drawable);
    }

    public void setPreview3Background(int color) {
        this.preview3.setBackgroundColor(color);
    }

    public void setPreview4Resource(int resource) {
        this.preview4.setImageResource(resource);
    }

    public void setPreview4Bitmap(Bitmap bitmap) {
        this.preview4.setImageBitmap(bitmap);
    }

    public void setPreview4Drawable(Drawable drawable) {
        this.preview4.setImageDrawable(drawable);
    }

    public void setPreview4Background(int color) {
        this.preview4.setBackgroundColor(color);
    }
}