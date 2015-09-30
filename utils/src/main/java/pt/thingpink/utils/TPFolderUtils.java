package pt.thingpink.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class TPFolderUtils {

	public static File getMoviesDataFolder(Context context) {

		String sdState = android.os.Environment.getExternalStorageState();

		if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
			return context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
		} else
			return context.getFilesDir();
	}

	public static String getMoviesDataFolderPath(Context context) {

		String sdState = android.os.Environment.getExternalStorageState();

		if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
			return context.getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath();
		} else
			return context.getFilesDir().getAbsolutePath();
	}

	public static File getPicturesDataFolder(Context context) {

		String sdState = android.os.Environment.getExternalStorageState();

		if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
			return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		} else
			return context.getFilesDir();
	}

	public static String getPicturesDataFolderPath(Context context) {

		String sdState = android.os.Environment.getExternalStorageState();

		if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
			return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
		} else
			return context.getFilesDir().getAbsolutePath();
	}

	public static File getDownloadsDataFolder(Context context) {

		String sdState = android.os.Environment.getExternalStorageState();

		if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
			return context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
		} else
			return context.getFilesDir();
	}

	public static String getDownloadsDataFolderPath(Context context) {

		String sdState = android.os.Environment.getExternalStorageState();

		if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
			return context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
		} else
			return context.getFilesDir().getAbsolutePath();
	}

	public static File getMusicDataFolder(Context context) {

		String sdState = android.os.Environment.getExternalStorageState();

		if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
			return context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
		} else
			return context.getFilesDir();
	}

	public static String getMusicDataFolderPath(Context context) {

		String sdState = android.os.Environment.getExternalStorageState();

		if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
			return context.getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath();
		} else
			return context.getFilesDir().getAbsolutePath();
	}

	public static File getCacheDirectory(Context context) {

		String sdState = android.os.Environment.getExternalStorageState();
		File cacheDir;

		if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
			// File sdDir =
			// android.os.Environment.getExternalStorageDirectory();

			cacheDir = context.getExternalCacheDir();
		} else
			cacheDir = context.getCacheDir();

		if (!cacheDir.exists())
			cacheDir.mkdirs();

		return cacheDir;
	}

	public static File getDataDirectory(Context context) {
		return context.getFilesDir();
	}
}
