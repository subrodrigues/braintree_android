package pt.thingpink.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TPToolsUtils {

	public static void printApplicationKeyHash(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

			for (android.content.pm.Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
			Log.e("KeyHash", e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			Log.e("KeyHash", e.getMessage());
		}
	}

	public static void playSound(Context context, int rawSound) {

		if (context == null)
			return;
		MediaPlayer mp = MediaPlayer.create(context, rawSound);

		mp.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
		mp.start();
	}

	public static void vibrate(Context context, long ms) {
		final Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		vibe.vibrate(ms);
	}

	public static boolean appInstalledOrNot(Context context, String uri) {
		PackageManager pm = context.getPackageManager();
		boolean app_installed = false;
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			app_installed = false;
		}
		return app_installed;
	}

	public static void openUrl(Context context, String url, String errorMessageToast) {

		if (TPNetworkUtils.checkInternetConnectionToast(context, true, errorMessageToast)) {
			if (!url.startsWith("http://") && !url.startsWith("https://")) {
				url = "http://" + url;
			}

			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
		}
	}

	public static void openMarketUrl(Context context, String packageName) {
		context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://play.google.com/store/apps/details?id=%s", packageName))));
	}

	public static void openUrl(Context context, String url) {

		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			url = "http://" + url;
		}

		context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}

	public static boolean intToBoolean(int value) {

		if (value == 1) {
			return true;
		} else {
			return false;
		}
	}

	public static int dpToPx(Resources res, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
	}

	public static int getVersionCode(Context context) {

		PackageInfo pInfo;
		try {
			pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	public static String getVersionName(Context context) {

		PackageInfo pInfo;
		try {
			pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model;
		}
	}

	private static String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}

    public static void openFile(Context context, File url) throws IOException {
        // Create URI
        File file=url;
        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file
        if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if(url.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if(url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if(url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if(url.toString().contains(".zip") || url.toString().contains(".rar")) {
            // WAV audio file
            intent.setDataAndType(uri, "application/x-wav");
        } else if(url.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if(url.toString().contains(".wav") || url.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if(url.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if(url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if(url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if(url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            //if you want you can also define the intent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(uri, "*/*");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void downloadFile(Context context, String url, String filename){

		DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));

        // This put the download in the same Download dir the browser uses
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

        // When downloading music and videos they will be listed in the player
        // (Seems to be available since Honeycomb only)
        r.allowScanningByMediaScanner();

        // Notify user when download is completed
        // (Seems to be available since Honeycomb only)
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // Start download
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        dm.enqueue(r);
    }

	public static void openNavigation(Context ctx, double latitude, double longitude){
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(String.format("http://maps.google.com/maps?daddr=%s,%s", String.valueOf(latitude).replace(",", "."), String.valueOf(longitude).replace(",", "."))));
		ctx.startActivity(intent);
	}

	public static void openSendEmail(Context ctx, String to, String subject, String message) {
		final Intent emailIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + to));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		emailIntent.putExtra(Intent.EXTRA_TEXT, message);
		ctx.startActivity(emailIntent);
	}

	public static void openCall(Context ctx, String phone){

		try {
			Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
			ctx.startActivity(intent);
		}
		catch(Exception ex){

		}
	}

	public static <T> T[] listToArray(List<T> list, Class<T> classObject){
		T[] ts = (T[]) Array.newInstance(classObject, list.size());
		return list.toArray(ts);
	}

	public static <T> List<T> arrayToList(T[] array){
		return new ArrayList<>(Arrays.asList(array));
	}

	public static String getScale(Context context) {
		String scale = "";
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		switch (metrics.densityDpi) {
			case DisplayMetrics.DENSITY_LOW:
				scale = "ldpi";
				break;
			case DisplayMetrics.DENSITY_MEDIUM:

				scale = "mdpi";
				break;
			case DisplayMetrics.DENSITY_HIGH:
				scale = "hdpi";
				break;
			case DisplayMetrics.DENSITY_XHIGH:
				scale = "xhdpi";
				break;
			case DisplayMetrics.DENSITY_XXHIGH:
				scale = "xxhdpi";
				break;
			case DisplayMetrics.DENSITY_XXXHIGH:
				scale = "xxxhdpi";
				break;
		}
		return scale;
	}

	public static String getMetadata(Context context, String name) {
		try {
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
			if (appInfo.metaData != null) {
				return appInfo.metaData.getString(name);
			}
		} catch (PackageManager.NameNotFoundException e) {
		}

		return null;
	}
}
