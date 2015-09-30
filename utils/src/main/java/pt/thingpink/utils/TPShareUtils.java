package pt.thingpink.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.widget.Toast;

public class TPShareUtils {

	// Facebook
	// public static void shareFacebook(final Context ctx, String name, String
	// caption, String description, String picture, String link) {
	// if
	// (Session.getActiveSession().isPermissionGranted(TPApplication.FACEBOOK_WRITE_PERMISSIONS.get(0))
	// == false) {
	// Session.getActiveSession().requestNewPublishPermissions(new
	// NewPermissionsRequest((Activity) ctx,
	// TPApplication.FACEBOOK_WRITE_PERMISSIONS));
	// return;
	// }
	// Bundle params = new Bundle();
	// if (!TextUtils.isEmpty(picture))
	// params.putString("picture", picture);
	// params.putString("name", name);
	// params.putString("caption", caption);
	// params.putString("description", description);
	// params.putString("link", link);
	//
	// Request request = new Request(Session.getActiveSession(), "me/feed",
	// params, HttpMethod.POST);
	//
	// request.setCallback(new Request.Callback() {
	//
	// @Override
	// public void onCompleted(Response response) {
	//
	// if (response.getError() == null) {
	// Toast.makeText(ctx, ctx.getString(R.string.share_success),
	// Toast.LENGTH_SHORT).show();
	// }
	// // User removed post permission from his application
	// // list on Facebook.com
	// else if (response.getError().getErrorCode() == 200) {
	// Log.e(TPApplication.TAG_APPNAME_ERROR,
	// "User removed post permission from his application list on Facebook.com");
	// Toast.makeText(ctx, ctx.getString(R.string.facebook_error_code_200),
	// Toast.LENGTH_SHORT).show();
	// Session.getActiveSession().closeAndClearTokenInformation();
	// TPApplication.facebookSession = Session.getActiveSession();
	// }
	// // User removed application from his application list on
	// // Facebook.com
	// else if (response.getError().getErrorCode() == 190) {
	// Log.e(TPApplication.TAG_APPNAME_ERROR,
	// "User removed application from his application list on Facebook.com");
	// Toast.makeText(ctx, ctx.getString(R.string.facebook_error_code_190),
	// Toast.LENGTH_SHORT).show();
	// Session.getActiveSession().closeAndClearTokenInformation();
	// TPApplication.facebookSession = Session.getActiveSession();
	// }
	// // General error
	// else {
	// Log.e(TPApplication.TAG_APPNAME_ERROR, "Facebook Error: "
	// + response.getError().toString());
	// Toast.makeText(ctx, ctx.getString(R.string.facebook_general_error),
	// Toast.LENGTH_SHORT).show();
	// Session.getActiveSession().closeAndClearTokenInformation();
	// TPApplication.facebookSession = Session.getActiveSession();
	// }
	// }
	// });
	//
	// request.executeAsync();
	// }

	public static void shareEmailHtml(Context ctx, String subject, String message, String intentTitle) {
		final Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(message));

		ctx.startActivity(Intent.createChooser(emailIntent, intentTitle));
	}

	public static void shareEmail(Context ctx, String subject, String message, String intentTitle) {
		final Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		emailIntent.putExtra(Intent.EXTRA_TEXT, message);

		ctx.startActivity(Intent.createChooser(emailIntent, intentTitle));
	}

	// SMS
	public static void shareSMS(Context ctx, String text) {

		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.putExtra("sms_body", text);
		smsIntent.setType("vnd.android-dir/mms-sms");
		ctx.startActivity(smsIntent);
	}

	// Twitter
	public static void shareTwiter(Context ctx, String url, String text, String hashtag) {

		int nCharsEnd = hashtag.length();

		int nChar = 140 - nCharsEnd + 4;

		if (text.length() > nChar) {
			text = text.substring(0, nChar) + "... ";
		}

		String tweetUrl = ("https://twitter.com/intent/tweet?url="
				+ url.replace("&", "%26") + "&text=" + Uri.encode(text)
				+ "&hashtags=" + hashtag + "");

		Intent intentTwitter = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(tweetUrl));
		ctx.startActivity(intentTwitter);
	}

	// WhatsApp
	public static void shareWhatsApp(Context ctx, String text) {

		try {
			Intent waIntent = new Intent(Intent.ACTION_SEND);
			waIntent.setType("text/plain");
			waIntent.setPackage("com.whatsapp");
			waIntent.putExtra(Intent.EXTRA_TEXT, text);
			ctx.startActivity(waIntent);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(ctx, ctx.getString(R.string.whatsapp_not_installed), Toast.LENGTH_SHORT).show();
		}

	}

	public static void shareNative(Context ctx, String title, String text, String intentTitle) {
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
		ctx.startActivity(Intent.createChooser(sharingIntent, intentTitle));
	}
}
