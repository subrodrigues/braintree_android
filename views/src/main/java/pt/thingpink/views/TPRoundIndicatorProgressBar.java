package pt.thingpink.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class TPRoundIndicatorProgressBar extends ProgressBar {

    private static final float CORNER_RADIUS_DP = 60.0f;

    public TPRoundIndicatorProgressBar(Context context) {
        this(context, null);
    }

    public TPRoundIndicatorProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TPRoundIndicatorProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void setProgressDrawable(Drawable d) {
        super.setProgressDrawable(tileify(d, false));
    }

    private Drawable tileify(Drawable drawable, boolean clip) {
        if (drawable instanceof LayerDrawable) {
            LayerDrawable background = (LayerDrawable) drawable;
            final int N = background.getNumberOfLayers();
            Drawable[] outDrawables = new Drawable[N];

            for (int i = 0; i < N; i++) {
                int id = background.getId(i);
                outDrawables[i] = tileify(background.getDrawable(i),
                        (id == android.R.id.progress || id == android.R.id.secondaryProgress));
            }

            LayerDrawable newBg = new LayerDrawable(outDrawables);

            for (int i = 0; i < N; i++) {
                newBg.setId(i, background.getId(i));
            }

            return newBg;

        } else {
            if (clip) {
                return new TPRoundClipDrawable(drawable, dp2px(getContext(), CORNER_RADIUS_DP));
            }
        }
        return drawable;
    }
    public static float dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale;
    }

}