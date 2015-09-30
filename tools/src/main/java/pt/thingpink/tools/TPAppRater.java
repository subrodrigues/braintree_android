package pt.thingpink.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.Toast;

public class TPAppRater {

    private static String packageName;
    public static boolean valid = false;

    public static void initAppRater(Context context, String appPackageName, int limitLaunches, int style, String text) {

        packageName = appPackageName;

        SharedPreferences prefs = context.getSharedPreferences("tp_rate_app", 0);

        if (prefs.getBoolean("dont_show_again", false)) {
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();

        // Add to launch Counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        if (launch_count >= limitLaunches) {
            editor.putLong("launch_count", 0);
            if (style == -1)
                showRateDialog(context);
            else
                showRateDialogCustomText(context, style, text);
        }

        editor.commit();
    }

    public static void initAppRater(Context context, String appPackageName, int limitLaunches) {
        initAppRater(context, appPackageName, limitLaunches, -1);
    }

    public static void initAppRater(Context context, String appPackageName, int limitLaunches, int style) {
        initAppRater(context, appPackageName, limitLaunches, style, null);
    }

    /**
     * Param styleBar used to create a costume rating bar. Pass "-1" if no customized style wanted.
     *
     * @param context
     * @param styleBar
     */
    public static void showRateDialogCustomText(final Context context, int styleBar, String text) {

        SharedPreferences prefs = context.getSharedPreferences("tp_rate_app", 0);
        final SharedPreferences.Editor editor = prefs.edit();

        Dialog dialog = new Dialog(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String message;
        if (TextUtils.isEmpty(text))
            message = context.getResources().getString(R.string.apprater_title)
                    + " "
                    + context.getResources().getString(R.string.app_name)
                    + ".\n\n "
                    + context.getResources().getString(R.string.apprater_description);
        else
            message = text;

        builder.setIcon(R.drawable.ic_launcher);

        final RatingBar rating;
        if (styleBar != -1)
            rating = new RatingBar(new ContextThemeWrapper(context, styleBar), null, 0);
        else
            rating = new RatingBar(context);

        createRateDialog(context, editor, builder, message, rating);
    }

    public static void showRateDialogCustomized(final Context context, int styleBar) {
        showRateDialogCustomText(context, styleBar, null);
    }

    public static void showRateDialog(final Context context) {
        showRateDialogCustomized(context, -1);
    }

    private static void createRateDialog(final Context context, final SharedPreferences.Editor editor, AlertDialog.Builder builder, String message, final RatingBar rating) {
        Dialog dialog;
        rating.post(new Runnable() {

            @Override
            public void run() {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER;
                rating.setLayoutParams(params);
                rating.setMax(5);
                rating.setStepSize(1);
                rating.setNumStars(5);
            }
        });
        builder.setView(rating);
        builder.setMessage(message).setTitle(context.getResources().getString(R.string.app_name))

                .setCancelable(false)

                .setPositiveButton(context.getResources().getString(R.string.apprater_accept),

                        new DialogInterface.OnClickListener() {
                            // @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editor.putBoolean("dont_show_again", true);
                                editor.commit();
                                if (rating.getRating() >= 4) {

                                    if (checkInternetConnectionToast(context, true, context.getResources().getString(R.string.error_network)))
                                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="
                                                + packageName)));
                                } else {
                                    Toast.makeText(context, R.string.apprater_rating_thanks, Toast.LENGTH_SHORT).show();
                                }
                            }

                        }).setNeutralButton(context.getResources().getString(R.string.apprater_later),

                new DialogInterface.OnClickListener() {
                    // @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .setNegativeButton(context.getResources().getString(R.string.apprater_never),

                        new DialogInterface.OnClickListener() {
                            // @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (editor != null) {
                                    editor.putBoolean("dont_show_again", true);
                                    editor.commit();
                                }
                                dialog.dismiss();
                            }
                        });

        dialog = builder.create();
        dialog.show();
    }

    public static void validarIncrement() {
        valid = true;
    }

    public static void removeIncrement() {
        valid = false;
    }

    public static boolean checkInternetConnectionToast(Context ctx, boolean toast, String message) {

        ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();

        if (i == null) {

            if (toast) {
                Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
            }
            return false;

        } else if (!i.isConnected()) {

            if (toast) {
                Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
            }

            return false;

        } else if (!i.isAvailable()) {

            if (toast) {
                Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
            }
            return false;

        } else {
            return true;
        }
    }

}