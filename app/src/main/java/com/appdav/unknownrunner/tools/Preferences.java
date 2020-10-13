package com.appdav.unknownrunner.tools;

import android.content.SharedPreferences;

public class Preferences {

    private static SharedPreferences mPreferences;
    private static final String key = "highscore";
    public static final String PREF_NAME = "pref";

    public static void setupPreferences(SharedPreferences preferences) {
        mPreferences = preferences;
    }

    public static void writeHighScore(long highScore) {
        mPreferences.edit().putLong(key, highScore).apply();
    }

    public static long getHighScore() {
        return mPreferences.getLong(key, 0);
    }

}
