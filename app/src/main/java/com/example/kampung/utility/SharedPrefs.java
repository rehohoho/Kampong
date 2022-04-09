package com.example.kampung.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    final static String KEY_TELE = "telehandle";
    // if this does not work then put this line into a new class, "Constants"

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedpref = ctx.getSharedPreferences(KEY_TELE, Context.MODE_PRIVATE);
        return sharedpref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedpref = ctx.getSharedPreferences(KEY_TELE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }
}
