package pt.thingpink.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class TPNetworkUtils {

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

	public static boolean isWifiOn(Context context) {

		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.isWifiEnabled();
	}

	public static boolean isSyncOn() {
		return ContentResolver.getMasterSyncAutomatically();
	}

	public static boolean isBluetoothOn() {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (bluetoothAdapter == null)
			return false;

		return bluetoothAdapter.isEnabled();
	}

	public static boolean isGPSOn(Context context) {

		boolean hasGps = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);

		if (hasGps) {

			LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

			if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
				return true;
			}
		}

		return false;
	}


}
