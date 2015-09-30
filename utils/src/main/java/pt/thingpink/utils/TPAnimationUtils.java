package pt.thingpink.utils;


import android.widget.ProgressBar;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import java.text.NumberFormat;

/**
 * Created by José on 18/11/2014.
 */
public class TPAnimationUtils {

    public static void tweenFloatValue(final TextView textView, float minValue, float maxValue, int duration, final Animator.AnimatorListener animationListener) {

        ValueAnimator animator = ValueAnimator.ofFloat(minValue, maxValue);
        animator.setDuration(duration);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                NumberFormat formatter = NumberFormat.getNumberInstance();
                formatter.setMinimumFractionDigits(2);
                formatter.setMaximumFractionDigits(2);
                float value = ((Float) (animation.getAnimatedValue())).floatValue();
                textView.setText(formatter.format(value));
            }
        });

        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationStart(arg0);
            }
            @Override
            public void onAnimationRepeat(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationRepeat(arg0);
            }
            @Override
            public void onAnimationEnd(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationEnd(arg0);
            }
            @Override
            public void onAnimationCancel(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationCancel(arg0);
            }
        });

        animator.start();
    }

    public static void tweenIntValue(final TextView textView, int minValue, int maxValue, int duration, final Animator.AnimatorListener animationListener) {

        ValueAnimator animator = ValueAnimator.ofInt(minValue, maxValue);
        animator.setDuration(duration);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = ((Integer) (animation.getAnimatedValue())).intValue();
                textView.setText(String.valueOf(value));
            }
        });

        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationStart(arg0);
            }
            @Override
            public void onAnimationRepeat(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationRepeat(arg0);
            }
            @Override
            public void onAnimationEnd(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationEnd(arg0);
            }
            @Override
            public void onAnimationCancel(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationCancel(arg0);
            }
        });

        animator.start();
    }

    public static void tweenIntValue(final TextView textView, final String textToPut, int minValue, int maxValue, int duration, final Animator.AnimatorListener animationListener) {

        ValueAnimator animator = ValueAnimator.ofInt(minValue, maxValue);
        animator.setDuration(duration);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = ((Integer) (animation.getAnimatedValue())).intValue();
                textView.setText(String.format(textToPut, String.valueOf(value)));
            }
        });

        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationStart(arg0);
            }
            @Override
            public void onAnimationRepeat(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationRepeat(arg0);
            }
            @Override
            public void onAnimationEnd(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationEnd(arg0);
            }
            @Override
            public void onAnimationCancel(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationCancel(arg0);
            }
        });

        animator.start();
    }

    public static void tweenIntValue(final ProgressBar progressBar, int minValue, int maxValue, int duration, final Animator.AnimatorListener animationListener) {

        ValueAnimator animator = ValueAnimator.ofInt(minValue, maxValue);
        animator.setDuration(duration);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = ((Integer) (animation.getAnimatedValue())).intValue();
                progressBar.setProgress(value);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationStart(arg0);
            }
            @Override
            public void onAnimationRepeat(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationRepeat(arg0);
            }
            @Override
            public void onAnimationEnd(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationEnd(arg0);
            }
            @Override
            public void onAnimationCancel(Animator arg0) {

                if(animationListener != null)
                    animationListener.onAnimationCancel(arg0);
            }
        });

        animator.start();
    }
}
