package pt.thingpink.utils;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class TPDialogUtils {

    private final static String TAG = "TPFramework";

    public static void showAlert(Context context, String alertTitle, String alertMessage) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(alertTitle);

        alertDialogBuilder.setIcon(R.drawable.ic_launcher);
        alertDialogBuilder.setMessage(alertMessage).setCancelable(true).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void showAlertDialog(Context context, String title, String message, String positiveButtonText, String negativeButtonText, String neutralButtonText, final AlertDialogCallback inter) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);

        builder.setMessage(message);
        builder.setCancelable(false);

        if (!TextUtils.isEmpty(positiveButtonText))

            builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {

                    if (inter != null)
                        inter.positiveButtonClick();
                }
            });

        if (!TextUtils.isEmpty(negativeButtonText))

            builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    if (inter != null)
                        inter.negativeButtonClick();
                }
            });

        if (!TextUtils.isEmpty(neutralButtonText))

            builder.setNeutralButton(neutralButtonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    if (inter != null)
                        inter.neutralButtonClick();
                }
            });

        AlertDialog dialog = builder.create();
        dialog.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                inter.dismiss();
            }
        });
        dialog.show();

    }

    public static void showPromptDialog(Context context, String title, String message, String positiveButtonText, String negativeButtonText, final PromptDialogCallback inter) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);

        builder.setMessage(message);

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View promptView = layoutInflater.inflate(R.layout.prompt_layout, null);

        final EditText inputLayout = (EditText) promptView.findViewById(R.id.prompt_input);

        builder.setView(promptView);

        builder.setCancelable(false);

        if (!TextUtils.isEmpty(positiveButtonText))

            builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {

                    if (inter != null)
                        inter.positiveButtonClick(inputLayout.getText().toString());
                }
            });

        if (!TextUtils.isEmpty(negativeButtonText))

            builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    if (inter != null)
                        inter.negativeButtonClick();
                }
            });

        AlertDialog dialog = builder.create();
        dialog.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                inter.dismiss();
            }
        });
        dialog.show();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void createNotification(Context context, Class<?> activity, Bundle bundle, String ticker, String title, String message, int notID, int iconDrawable, Bitmap largeIcon, String bigText, int ledColor) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(iconDrawable).setTicker(ticker).setContentTitle(title).setContentText(message);
        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_SOUND);

        if (largeIcon != null)
            mBuilder.setLargeIcon(largeIcon);

        if (!TextUtils.isEmpty(bigText)) {
            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
            bigTextStyle.setBigContentTitle(title);
            // bigTextStyle.setSummaryText(message);
            bigTextStyle.bigText(bigText);
            mBuilder.setStyle(bigTextStyle);
        }

        if (activity != null) {

            Intent intent = new Intent(context, activity);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (bundle != null) {
                intent.putExtras(bundle);
            }

            PendingIntent pending = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pending);

        }

        mBuilder.setAutoCancel(true);
        mBuilder.setLights(ledColor, 500, 500);
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressWarnings("deprecation")
        WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG);
        wl.acquire(5000);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification not = mBuilder.build();
        not.flags |= Notification.FLAG_SHOW_LIGHTS
                | Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(notID, not);
    }
}
