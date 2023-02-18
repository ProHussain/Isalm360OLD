package com.hashmac.islam360.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    private static final String FAJR = "fajr";
    private static final String SUNRISE = "sunrise";
    private static final String DHUHR = "dhuhr";
    private static final String ASR = "asr";
    private static final String MAGHRIB = "maghrib";
    private static final String ISHA = "isha";
    private static final String DATE = "date";
    private static final String CITY = "city";
    private static final String MIDNIGHT = "midnight";
    private static final String VIEW_MODE = "view_mode";
    private static final String PUSH_NOTIFICATION = "push_notification";
    private static final String SOUND = "sound";
    private static final String VIBRATION = "vibration";
    private final SharedPreferences pref;

    public PrefManager(Context context) {
        pref = context.getSharedPreferences("mainPref", Context.MODE_PRIVATE);
    }

    public void updateLastRead(String name, int num, int position) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("verseName", name);
        editor.putInt("verseNum", num);
        editor.putInt("position", position);
        editor.apply();
    }

    public void updateLastReadJuz(int num, int position) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("juzNum", num);
        editor.putInt("juzPosition", position);
        editor.apply();
    }

    public String getVerseName() {
        return pref.getString("verseName", "");
    }

    public int getVerse() {
        return pref.getInt("verseNum", -1);
    }

    public int getSurahPosition() {
        return pref.getInt("position", 1);
    }


    public int getJuzNum() {
        return pref.getInt("juzNum", 1);
    }

    public int getJuzPosition() {
        return pref.getInt("juzPosition", -1);
    }

    public void saveLocation(double lat, double lon) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(LATITUDE, (float) lat);
        editor.putFloat(LONGITUDE, (float) lon);
        editor.apply();
    }

    public void saveViewMode(boolean isOn) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(VIEW_MODE, isOn);
        editor.apply();
    }

    public boolean getViewMode() {
        return pref.getBoolean(VIEW_MODE, false);
    }

    public void saveHijriDate(String date) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("hijri_date", date);
        editor.apply();
    }

    public String getHijriDate() {
        return pref.getString("hijri_date", "???");
    }

    public void savePrayerTimes(String fajr, String sunrise, String dhuhr, String asr,
                                String maghrib, String isha, String midnight,  String city,String date) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(FAJR, fajr);
        editor.putString(SUNRISE, sunrise);
        editor.putString(DHUHR, dhuhr);
        editor.putString(ASR, asr);
        editor.putString(MAGHRIB, maghrib);
        editor.putString(ISHA, isha);
        editor.putString(MIDNIGHT, midnight);
        editor.putString(DATE, date);
        editor.putString(CITY, city);
        editor.apply();
    }

    public String getFajr() {
        return pref.getString(FAJR, "03:44");
    }

    public String getSunrise() {
        return pref.getString(SUNRISE, "05:14");
    }

    public String getDhuhr() {
        return pref.getString(DHUHR, "12:04");
    }

    public String getAsr() {
        return pref.getString(ASR, "15:41");
    }

    public String getMaghrib() {
        return pref.getString(MAGHRIB, "18:54");
    }

    public String getIsha() {
        return pref.getString(ISHA, "20:25");
    }

    public String getMidnight() {
        return pref.getString(MIDNIGHT, "00:00");
    }
    public String getDate() {
        return pref.getString(DATE, "15-09-1443");
    }
    public String getCity() {
        return pref.getString(CITY, "Welcome Back!");
    }

    public double getLocationLat() {
        return pref.getFloat(LATITUDE, 0);
    }

    public double getLocationLon() {
        return pref.getFloat(LONGITUDE, 0);
    }

    public boolean getPushNotification() {
        return pref.getBoolean(PUSH_NOTIFICATION, true);
    }

    public boolean getSound() {
        return pref.getBoolean(SOUND, true);
    }

    public boolean getVibration() {
        return pref.getBoolean(VIBRATION, true);
    }

    public void setPushNotification(Boolean value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(PUSH_NOTIFICATION, value);
        editor.apply();
    }

    public void setSound(Boolean value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(SOUND, value);
        editor.apply();
    }

    public void setVibration(Boolean value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(VIBRATION, value);
        editor.apply();
    }

}
