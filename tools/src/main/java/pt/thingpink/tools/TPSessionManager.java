package pt.thingpink.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TPSessionManager {

	private SharedPreferences TPSessionSharedPreferences;

	private Editor TPEditor;

	private Context TPContext;

	// All Shared Preferences Keys
	// Access Token (make variable public to access from outside)
	public static final String TP_SESSION_TOKEN = "tp_session_token";

	// Constructor
	public TPSessionManager(Context context, SharedPreferences logingPref) {
		this.TPContext = context;
		this.TPSessionSharedPreferences = logingPref;
		TPEditor = TPSessionSharedPreferences.edit();
	}

	/**
	 * Create session
	 */
	public void createSession(HashMap<String, String> sessiondetails) {

		for (Entry<String, String> entry : sessiondetails.entrySet()) {
			TPEditor.putString(entry.getKey(), entry.getValue());
		}

		// commit changes
		TPEditor.commit();
	}

	/**
	 * Destroy session
	 */
	public void destroySession() {
		TPEditor.clear();
		TPEditor.commit();
	}

	/**
	 * Check session
	 */
	public boolean checkSession() {

		if (TPSessionSharedPreferences.contains(TP_SESSION_TOKEN)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get session
	 */

	public HashMap<String, String> getSessionDetails() {

		Map<String, ?> sessionStored = TPSessionSharedPreferences.getAll();
		HashMap<String, String> sessionDetails = new HashMap<String, String>();

		for (Entry<String, ?> entry : sessionStored.entrySet()) {
			sessionDetails.put(entry.getKey(), String.valueOf(entry.getValue()));
		}

		return sessionDetails;
	}

}
