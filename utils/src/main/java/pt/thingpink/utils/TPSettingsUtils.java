package pt.thingpink.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

public class TPSettingsUtils {

    public static final String PREFERENCES_NAME = "preferences";
    private static final String PREFERENCE_PUSH = "pushNotificationEnabled";
    private static final String PREFERENCE_SOUND = "soundEnabled";
    private static final String PREFERENCE_FIRST_TIME = "firstTimeApplication";
    public static final String PREFERENCES_POPUP = "popup";

    public static void savePushNotificationsSettings(Context context, boolean isEnabled) {
        Editor editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(PREFERENCE_PUSH, isEnabled);
        editor.commit();
    }

    public static boolean isPushNotificationEnabled(Context context) {
        SharedPreferences editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return editor.getBoolean(PREFERENCE_PUSH, true);
    }

    public static void saveSoundEnabledSettings(Context context, boolean isEnabled) {
        Editor editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(PREFERENCE_SOUND, isEnabled);
        editor.commit();
    }

    public static boolean isSoundEnabled(Context context) {
        SharedPreferences editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return editor.getBoolean(PREFERENCE_SOUND, true);
    }

    public static void saveFirstTimeApplication(Context context, boolean isFirstTime) {
        Editor editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(PREFERENCE_FIRST_TIME, isFirstTime);
        editor.commit();
    }

    public static boolean isFirstTimeApplication(Context context) {
        SharedPreferences editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return editor.getBoolean(PREFERENCE_FIRST_TIME, true);
    }


    public static void setLastTimePopup(Context context, long time) {
        Editor editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        editor.putLong(PREFERENCES_POPUP, time);
        editor.commit();
    }

    public static boolean showPopup(Context context, long interval) {
        SharedPreferences editor = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        long lastTime = editor.getLong(PREFERENCES_POPUP, 0);
        if (interval < (System.currentTimeMillis() - lastTime))
            return true;
        else
            return false;
    }

    public static <T> void saveObjectToSettings(Context context, String name, T object) {
        SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        prefsEditor.putString(name, json);
        prefsEditor.commit();
    }

    public static <T> T getObjectFromSettings(Context context, String name, Class<T> objectClass) {
        SharedPreferences mPrefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(name, "");
        T obj = gson.fromJson(json, objectClass);
        return obj;
    }
}
