package com.demo.example.OS.until;

import android.content.Context;
import android.content.SharedPreferences;


public class MyShare {
    private static SharedPreferences share(Context context) {
        return context.getSharedPreferences("preferences", 0);
    }

    public static String getPhoto(Context context) {
        return share(context).getString("photo", "");
    }

    public static void putPhoto(Context context, String str) {
        share(context).edit().putString("photo", str).apply();
    }

    public static void putStyle(Context context, int i) {
        share(context).edit().putInt("style", i).apply();
    }

    public static int getStyle(Context context) {
        return share(context).getInt("style", 1);
    }

    public static void putDark(Context context, boolean z) {
        share(context).edit().putBoolean("dark", z).apply();
    }

    public static boolean getDark(Context context) {
        return share(context).getBoolean("dark", false);
    }

    public static void putSoundPad(Context context, int i) {
        share(context).edit().putInt("sound_pad", i).apply();
    }

    public static int getSoundPad(Context context) {
        return share(context).getInt("sound_pad", 0);
    }
}
