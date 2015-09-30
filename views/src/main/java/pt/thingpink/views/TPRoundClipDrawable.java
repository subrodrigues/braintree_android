package pt.thingpink.views;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * Round Clip Drawable
 * like ClipDrawable, but with round corner
 * Created by zhangge on 14-8-5.
 */
public class TPRoundClipDrawable extends Drawable implements Drawable.Callback{

    private final Rect mTmpRect = new Rect();
    private final Path mTmpPath = new Path();
    private float[] mRadius;
    private final RectF mTmpRectF = new RectF();



    private ClipState mClipState;

    /**
     * @param drawable
     */
    public TPRoundClipDrawable(Drawable drawable, float radius) {
        this(null, null);
        float[] radii = null;
        if (radius > 0) {
            radii = new float[8];
            for (int i = 0; i < 8; i++) {
                radii[i] = radius;
            }
        }
        mRadius = radii;
        mClipState.mDrawable = drawable;
    }

    // overrides from Drawable.Callback

    public void invalidateDrawable(Drawable who) {
        if (Build.VERSION.SDK_INT >= 11) {
            final Callback callback = getCallback();
            if (callback != null) {
                callback.invalidateDrawable(this);
            }
        }
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (Build.VERSION.SDK_INT >= 11) {
            final Callback callback = getCallback();
            if (callback != null) {
                callback.scheduleDrawable(this, what, when);
            }
        }
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        if (Build.VERSION.SDK_INT >= 11) {
            final Callback callback = getCallback();
            if (callback != null) {
                callback.unscheduleDrawable(this, what);
            }
        }
    }

    // overrides from Drawable

    // overrides from Drawable

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations()
                | mClipState.mChangingConfigurations
                | mClipState.mDrawable.getChangingConfigurations();
    }

    @Override
    public boolean getPadding(Rect padding) {
        // XXX need to adjust padding!
        return mClipState.mDrawable.getPadding(padding);
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        mClipState.mDrawable.setVisible(visible, restart);
        return super.setVisible(visible, restart);
    }

    @Override
    public void setAlpha(int alpha) {
        mClipState.mDrawable.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mClipState.mDrawable.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return mClipState.mDrawable.getOpacity();
    }

    @Override
    public boolean isStateful() {
        return mClipState.mDrawable.isStateful();
    }

    @Override
    protected boolean onStateChange(int[] state) {
        return mClipState.mDrawable.setState(state);
    }

    @Override
    protected boolean onLevelChange(int level) {
        mClipState.mDrawable.setLevel(level);
        invalidateSelf();
        return true;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mClipState.mDrawable.setBounds(bounds);
    }

    @Override
    public int getIntrinsicWidth() {
        return mClipState.mDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mClipState.mDrawable.getIntrinsicHeight();
    }

    @Override
    public ConstantState getConstantState() {
        if (mClipState.canConstantState()) {
            mClipState.mChangingConfigurations = getChangingConfigurations();
            return mClipState;
        }
        return null;
    }
    @Override
    public void draw(Canvas canvas) {
        final Rect r = mTmpRect;
        final Rect bounds = getBounds();
        int level = getLevel();
        int w = bounds.width();
        mTmpPath.reset();
        final int iw = 0; //mClipState.mDrawable.getIntrinsicWidth();
        w -= (w - iw) * (10000 - level) / 10000;
        int h = bounds.height();
        r.set(bounds);
        r.right = r.left + w;
        mTmpRectF.set(r);
        mTmpPath.addRoundRect(mTmpRectF, mRadius, Path.Direction.CW);
        if (w > 0 && h > 0) {
            canvas.save();
            canvas.clipPath(mTmpPath);
            mClipState.mDrawable.draw(canvas);
            canvas.restore();
        }
    }

    final static class ClipState extends ConstantState {
        Drawable mDrawable;
        int mChangingConfigurations;

        private boolean mCheckedConstantState;
        private boolean mCanConstantState;

        ClipState(ClipState orig, TPRoundClipDrawable owner, Resources res) {
            if (orig != null) {
                if (res != null) {
                    mDrawable = orig.mDrawable.getConstantState().newDrawable(res);
                } else {
                    mDrawable = orig.mDrawable.getConstantState().newDrawable();
                }
                mDrawable.setCallback(owner);
                mCheckedConstantState = mCanConstantState = true;
            }
        }

        @Override
        public Drawable newDrawable() {
            return new TPRoundClipDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources res) {
            return new TPRoundClipDrawable(this, res);
        }

        @Override
        public int getChangingConfigurations() {
            return mChangingConfigurations;
        }

        boolean canConstantState() {
            if (!mCheckedConstantState) {
                mCanConstantState = mDrawable.getConstantState() != null;
                mCheckedConstantState = true;
            }

            return mCanConstantState;
        }
    }

    private TPRoundClipDrawable(ClipState state, Resources res) {
        mClipState = new ClipState(state, this, res);
    }
}