package pt.thingpink.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.widget.Toast;

public class TPImageUtils {
	private static final String TAG = "ImageUtils";

	public static Bitmap makeMaskImage(Context ctx, Bitmap original, int bitmapResource, int frameResource, int maskResource) {
		Resources resources = ctx.getResources();
		float scale = resources.getDisplayMetrics().density;

		Bitmap bitmap = BitmapFactory.decodeResource(resources, bitmapResource);
		Bitmap frame = BitmapFactory.decodeResource(resources, frameResource);
		Bitmap mask = BitmapFactory.decodeResource(resources, maskResource);

		Bitmap.Config bitmapConfig = bitmap.getConfig();
		// set default bitmap config if none
		if (bitmapConfig == null) {
			bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
		}
		// resource bitmaps are imutable,
		// so we need to convert it to mutable one
		bitmap = bitmap.copy(bitmapConfig, true);

		// Scale to target size
		original = Bitmap.createScaledBitmap(original, mask.getWidth(), mask.getHeight(), true);
		Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
		Canvas mCanvas = new Canvas(result);
		Paint paint = new Paint();
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
		mCanvas.drawBitmap(original, 0, 0, null);
		mCanvas.drawBitmap(mask, 0, 0, paint);

		mCanvas = new Canvas(bitmap);
		// new antialised Paint
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// text color - #3D3D3D
		paint.setColor(Color.rgb(0, 0, 0));
		// text size in pixels
		paint.setTextSize((int) (10 * scale));

		int offset = (bitmap.getWidth() - result.getWidth()) / 2;

		mCanvas.drawBitmap(result, offset, offset, null);
		mCanvas.drawBitmap(frame, 0, 0, null);

		return bitmap;
	}

	public static Bitmap decodeSampledBitmapFromLocalUrl(String url, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(url, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(url, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	public static void addImageToGallery(final String filePath, final Context context, String successMessage) {

		ContentValues values = new ContentValues();

		values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
		values.put(Images.Media.MIME_TYPE, "image/jpeg");
		values.put(MediaStore.MediaColumns.DATA, filePath);

		context.getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values);
		Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show();
	}
}
