package com.hashmac.islam360.activity;


import static com.example.easywaylocation.EasyWayLocation.LOCATION_SETTING_REQUEST_CODE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.GetLocationDetail;
import com.example.easywaylocation.Listener;
import com.example.easywaylocation.LocationData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.hashmac.islam360.BuildConfig;
import com.hashmac.islam360.R;
import com.hashmac.islam360.model.Azkar;
import com.hashmac.islam360.model.Prayer;
import com.hashmac.islam360.service.ReminderReciver;
import com.hashmac.islam360.utilities.Counter;
import com.hashmac.islam360.utilities.DataBaseHandler;
import com.hashmac.islam360.utilities.PrefManager;
import com.hashmac.islam360.utilities.Utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class MainActivity extends AppCompatActivity implements Listener, LocationData.AddressCallBack {

    public static final int INTERVAL = 10 * 60 * 1000;

    DataBaseHandler db;
    public static final int QOD = 8;
    Locale myLocale;
    public static final String USERLANGUAGES = "user_langu";
    private SharedPreferences sharedPreferences;
    private EasyWayLocation easyWayLocation;
    private GetLocationDetail getLocationDetail;

    private TextView cIsha;
    private TextView cFajr;
    private TextView cSunrise;
    private TextView cDhuhr;
    private TextView cAsr;
    private TextView cMaghrib;
    private TextView cHijriDate;
    private Location detectedLocation;
    private GitHubService service;
    private TextView time;
    private TextView salat;
    private TextView timeZone;
    private ImageView curentImage;
    private PrefManager prefManager;
    private TextView dailyAzkar;
    private LinearLayout versionContainer;
    private Button versionBtn;

    FirebaseRemoteConfig firebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        String userlango = preferences.getString(USERLANGUAGES, "en");

        Locale locale = new Locale(userlango);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        sharedPreferences = getSharedPreferences("checkFail1", MODE_PRIVATE);
        db = new DataBaseHandler(this);
        db.openDataBase();
        db.refresh();
        db.recreate();

        prefManager = new PrefManager(this);

        easyWayLocation = new EasyWayLocation(this, true, this);

        getLocationDetail = new GetLocationDetail(this, this);

        if (permissionIsGranted()) {
            doLocationWork();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_SETTING_REQUEST_CODE);
        }

        time = findViewById(R.id.time);
        salat = findViewById(R.id.salat);
        curentImage = findViewById(R.id.current_image);
        timeZone = findViewById(R.id.timeZone);

        cFajr = findViewById(R.id.calendar_fajr);
        cSunrise = findViewById(R.id.calendar_sunrise);
        cDhuhr = findViewById(R.id.calendar_dhuhr);
        cAsr = findViewById(R.id.calendar_asr);
        cMaghrib = findViewById(R.id.calendar_maghrib);
        cIsha = findViewById(R.id.calendar_isha);
        cHijriDate = findViewById(R.id.hijri_date);
        versionContainer = findViewById(R.id.new_version);
        versionBtn = findViewById(R.id.btnUpdateVersion);



        TextView lFajr = findViewById(R.id.l_fajr);
        TextView lSunrise = findViewById(R.id.l_sunrise);
        TextView lDhuhr = findViewById(R.id.l_dhuhr);
        TextView lAsr = findViewById(R.id.l_asr);
        TextView lMaghrib = findViewById(R.id.l_maghrib);
        TextView lIsha = findViewById(R.id.l_isha);
        dailyAzkar = findViewById(R.id.textDailyAzkar);

        lFajr.setText(getResources().getString(R.string.fajr));
        lSunrise.setText(getResources().getString(R.string.sunrise));
        lDhuhr.setText(getResources().getString(R.string.dhuhr));
        lAsr.setText(getResources().getString(R.string.asr));
        lMaghrib.setText(getResources().getString(R.string.maghrib));
        lIsha.setText(getResources().getString(R.string.isha));

        loadPrayerTimes();
        // Azkar of the day
        AzkarOfTheDay();

        LinearLayout azkar = findViewById(R.id.btn_azkar);
        LinearLayout qibla = findViewById(R.id.btn_qibla);
        LinearLayout reminder = findViewById(R.id.btn_reminder);
        LinearLayout favorites = findViewById(R.id.btn_favorites);
        LinearLayout categories = findViewById(R.id.btn_categories);
        LinearLayout quran = findViewById(R.id.btn_quran);

        azkar.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this,
                    TasbehActivity.class);
            startActivity(intent);
        });

        qibla.setOnClickListener(view -> {
            String city = prefManager.getCity();
            Intent qiblaIntent = new Intent(MainActivity.this, QiblaActivity.class);
            qiblaIntent.putExtra("City",city);
            startActivity(qiblaIntent);
        });

        reminder.setOnClickListener(view -> {
            Intent azkarReminder = new Intent(MainActivity.this,
                    AzkarsTimeSettingsActivity.class);
            startActivity(azkarReminder);
        });

        favorites.setOnClickListener(view -> {

            Intent favorites1 = new Intent(MainActivity.this,
                    AzkarsActivity.class);
            favorites1.putExtra("mode", "fav");
            startActivity(favorites1);

        });

        categories.setOnClickListener(view -> {

            Intent category = new Intent(MainActivity.this,
                    CategoryActivity.class);
            startActivity(category);

        });

        quran.setOnClickListener(view -> {
            Intent hizb = new Intent(MainActivity.this,
                    Quranlist.class);

            startActivity(hizb);

        });

        ImageView setting = findViewById(R.id.setting_icon);
        setting.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,SettingActivity.class));
        });


        newVersionCheck();
        versionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName()));
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e) {
                    startActivity(intent);
                }
            }
        });
    }

    private void newVersionCheck() {
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(1).build();
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Boolean> task) {
                if (task.isSuccessful()) {
                    if (BuildConfig.VERSION_CODE < firebaseRemoteConfig.getLong("latest_version_of_app")) {
                        versionContainer.setVisibility(View.VISIBLE);
                    } else {
                        versionContainer.setVisibility(View.GONE);
                    }
                }
            }
        });
    }


    private void AzkarOfTheDay() {
        Azkar azkar = db.getAzkar(random());
        dailyAzkar.setText(azkar.getArabic());
    }

    private void loadPrayerTimes() {
        String fajr = prefManager.getFajr();
        String sunrise = prefManager.getSunrise();
        String dhuhr = prefManager.getDhuhr();
        String asr = prefManager.getAsr();
        String maghrib = prefManager.getMaghrib();
        String isha = prefManager.getIsha();
        String midnight = prefManager.getMidnight();
        String date = prefManager.getDate();
        String city = prefManager.getCity();

        try {
            showDialog(fajr, sunrise, dhuhr, asr, maghrib, isha,city,date);
        } catch (ParseException e) {

        }

        boolean isNextDay = false;
        String currentVal = "";

        if (System.currentTimeMillis() < prayerToMillis(fajr)) {
            currentVal = fajr;
            //time.setText(fajr);
            salat.setText(getResources().getString(R.string.fajr));
            curentImage.setImageResource(R.drawable.shalat);
        } else if (System.currentTimeMillis() > prayerToMillis(fajr) && System.currentTimeMillis() < prayerToMillis(sunrise)) {
            currentVal = sunrise;
            //time.setText(sunrise);
            salat.setText(getResources().getString(R.string.sunrise));
            curentImage.setImageResource(R.drawable.sun);
        } else if (System.currentTimeMillis() > prayerToMillis(sunrise) && System.currentTimeMillis() < prayerToMillis(dhuhr)) {
            currentVal = dhuhr;
            //time.setText(dhuhr);
            salat.setText(getResources().getString(R.string.dhuhr));
            curentImage.setImageResource(R.drawable.islam);
        } else if (System.currentTimeMillis() > prayerToMillis(dhuhr) && System.currentTimeMillis() < prayerToMillis(asr)) {
            currentVal = asr;
            //time.setText(asr);
            salat.setText(getResources().getString(R.string.asr));
            curentImage.setImageResource(R.drawable.ruku);
        } else if (System.currentTimeMillis() > prayerToMillis(asr) && System.currentTimeMillis() < prayerToMillis(maghrib)) {
            currentVal = maghrib;
            //time.setText(maghrib);
            salat.setText(getResources().getString(R.string.maghrib));
            curentImage.setImageResource(R.drawable.prayer);
        } else if (System.currentTimeMillis() > prayerToMillis(maghrib) && System.currentTimeMillis() < prayerToMillis(isha)) {
            currentVal = isha;
            //time.setText(isha);
            salat.setText(getResources().getString(R.string.isha));
            curentImage.setImageResource(R.drawable.islamic);
        } else {
            isNextDay = true;
            currentVal = fajr;
            //time.setText(fajr);
            salat.setText(getResources().getString(R.string.fajr));
            curentImage.setImageResource(R.drawable.shalat);
        }


        long targetMillis;

        //targetMillis = CountDownTask.elapsedRealtime() + (prayerToMillis(currentVal) - System.currentTimeMillis());

        targetMillis = prayerToMillis(currentVal) - now();

        Counter.getInstance(time, targetMillis);



        Log.d("zzz", "loadPrayerTimes: ");
    }


    private void showDialog(String fajr, String sunrise, String dhuhr, String asr, String maghrib, String isha, String city, String date) throws ParseException {

        String fajrT = format12HourFormat(fajr);
        String sunriseT = format12HourFormat(sunrise);
        String dhuhrT = format12HourFormat(dhuhr);
        String asrT = format12HourFormat(asr);
        String maghribT = format12HourFormat(maghrib);
        String ishaT = format12HourFormat(isha);

        cFajr.setText(fajrT);
        cSunrise.setText(sunriseT);
        cDhuhr.setText(dhuhrT);
        cAsr.setText(asrT);
        cMaghrib.setText(maghribT);
        cIsha.setText(ishaT);
        cHijriDate.setText(date);
        timeZone.setText(city);
    }


    @SuppressLint("SimpleDateFormat")
    private String format12HourFormat(String val) throws ParseException {
        SimpleDateFormat formatVal = new SimpleDateFormat("HH:mm");

        Date result = formatVal.parse(val);

        SimpleDateFormat format = new SimpleDateFormat("h:mm a");

        return format.format(result);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefManager.getPushNotification()) {
            int position = random();
            if (db != null) {
                int minutes = sharedPreferences.getInt("minutes", 60);
                Log.d("TAG", "onCreate: " + db.getAllAzkar("").size());
                startReminder(minutes, 111, db.getAzkar(position).getCategory(), db.getAzkar(position).getAzkar(), position);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isSet", true);
                editor.apply();
            }
        }
    }


    public boolean permissionIsGranted() {

        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void doLocationWork() {
        easyWayLocation.startLocation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_SETTING_REQUEST_CODE) {
            easyWayLocation.onActivityResult(resultCode);
        }
    }

    private int random() {
        return new Random().nextInt(266);
    }

    private void startReminder(int minutes, int notificationID, String title, String description, int position) {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReciver.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("notificationID", notificationID);
        intent.putExtra("position", position);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getBroadcast(this, notificationID, intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            pendingIntent = PendingIntent.getBroadcast(this, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 60 * 2, (long) 1000 * 60 * minutes, pendingIntent);
    }

    public void saveuserlang(String lang) {
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
        prefEditor.putString(USERLANGUAGES, lang);
        prefEditor.apply();
    }

    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent rego = new Intent(this, MainActivity.class);
        startActivity(rego);
        this.recreate();
        restart();
        this.finish();
    }


    @Override
    public void onStart() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userlango = preferences.getString(USERLANGUAGES, "en");

        if (userlango != null) {

            Locale locale = new Locale(userlango);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }

        super.onStart();
    }


    public void restart() {
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
        db.recreate();
    }

    public void reload() {
        db.close();
        copybase();
        db.refresh();
        db = new DataBaseHandler(this);
        db.openDataBase();
        db.getAllCategories();
    }

    protected void copybase() {
        db = new DataBaseHandler(this);

        try {
            db.copyDataBaseFromAsset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void recreate() {
        super.recreate();
        db.recreate();
    }

    @Override
    public void locationOn() {

    }

    @Override
    public void currentLocation(Location location) {

        detectedLocation = location;
        getLocationDetail.getAddress(location.getLatitude(), location.getLongitude(), "xyz");
    }

    @Override
    public void locationCancelled() {

    }

    @Override
    public void locationData(LocationData locationData) {

        loadPrayerTimes(detectedLocation.getLatitude(), detectedLocation.getLongitude());
    }

    public interface GitHubService {
        @GET("timings")
        Call<Prayer> getTime(@Query("latitude") String lat,
                             @Query("longitude") String lon);
    }


    private void loadPrayerTimes(double lat, double lon) {
        prefManager.saveLocation(lat, lon);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.aladhan.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        service = retrofit.create(GitHubService.class);

        String latitude = String.valueOf(lat);
        String longetude = String.valueOf(lon);

        service.getTime(latitude, longetude).enqueue(new Callback<Prayer>() {
            @Override
            public void onResponse(Call<Prayer> call, Response<Prayer> response) {
                if (response.isSuccessful()) {
                    String currentVal = "";
                    boolean isNextDay = false;
                    String fajr = response.body().getData().getTimings().getFajr();
                    String sunrise = response.body().getData().getTimings().getSunrise();
                    String dhuhr = response.body().getData().getTimings().getDhuhr();
                    String asr = response.body().getData().getTimings().getAsr();
                    String maghrib = response.body().getData().getTimings().getMaghrib();
                    String isha = response.body().getData().getTimings().getIsha();
                    String midnight = response.body().getData().getTimings().getMidnight();
                    String date = response.body().getData().getDate().getHijri().getDate();
                    String city;
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(detectedLocation.getLatitude(), detectedLocation.getLongitude(), 1);
                        String cityName = addresses.get(0).getLocality();
                        String countryName = addresses.get(0).getCountryName();
                        city = cityName+", "+countryName;
                    } catch (IOException e) {
                        String timeZone1 = response.body().getData().getMeta().getTimeZone();
                        city = timeZone1.substring(timeZone1.indexOf("/")+1)+", "+timeZone1.substring(0,timeZone1.indexOf("/"));
                        e.printStackTrace();
                    }
                    prefManager.savePrayerTimes(fajr, sunrise, dhuhr, asr, maghrib, isha, midnight,city,date);
                    loadPrayerTimes();

                    if (System.currentTimeMillis() < prayerToMillis(fajr)) {
                        currentVal = fajr;
                        salat.setText(getResources().getString(R.string.fajr));
                    } else if (System.currentTimeMillis() > prayerToMillis(fajr) && System.currentTimeMillis() < prayerToMillis(sunrise)) {
                        currentVal = sunrise;
                        salat.setText(getResources().getString(R.string.sunrise));
                    } else if (System.currentTimeMillis() > prayerToMillis(sunrise) && System.currentTimeMillis() < prayerToMillis(dhuhr)) {
                        currentVal = dhuhr;
                        salat.setText(getResources().getString(R.string.dhuhr));
                    } else if (System.currentTimeMillis() > prayerToMillis(dhuhr) && System.currentTimeMillis() < prayerToMillis(asr)) {
                        currentVal = asr;
                        salat.setText(getResources().getString(R.string.asr));
                    } else if (System.currentTimeMillis() > prayerToMillis(asr) && System.currentTimeMillis() < prayerToMillis(maghrib)) {
                        currentVal = maghrib;
                        salat.setText(getResources().getString(R.string.maghrib));
                    } else if (System.currentTimeMillis() > prayerToMillis(maghrib) && System.currentTimeMillis() < prayerToMillis(isha)) {
                        currentVal = isha;
                        salat.setText(getResources().getString(R.string.isha));
                    } else if (System.currentTimeMillis() > prayerToMillis(isha)) {
                        currentVal = fajr;
                        isNextDay = true;
                        salat.setText(getResources().getString(R.string.fajr));
                    }


                    long targetMillis;

                    if (isNextDay) {
                        targetMillis = prayerToMillisNextDay(currentVal) - now();
                    } else {
                        targetMillis = prayerToMillis(currentVal) - now();
                    }


                    Counter.getInstance(time, targetMillis);

                    Utils utils = Utils.getInstance(MainActivity.this);
                    utils.resetAdhanAlarm(7701, prayerToMillis(fajr), prayerToMillisNextDay(fajr), "Fajr", true);
                    utils.resetAdhanAlarm(7702, prayerToMillis(dhuhr), prayerToMillisNextDay(dhuhr), "Dhuhr", true);
                    utils.resetAdhanAlarm(7703, prayerToMillis(asr), prayerToMillisNextDay(asr), "Asr", true);
                    utils.resetAdhanAlarm(7704, prayerToMillis(maghrib), prayerToMillisNextDay(maghrib), "Maghrib", true);
                    utils.resetAdhanAlarm(7705, prayerToMillis(isha), prayerToMillisNextDay(isha), "Isha", true);


                    utils.resetAdhanAlarm(7706, prayerToMillis(fajr) - INTERVAL, prayerToMillisNextDay(fajr) - INTERVAL, "Fajr", false);
                    utils.resetAdhanAlarm(7707, prayerToMillis(dhuhr) - INTERVAL, prayerToMillisNextDay(dhuhr) - INTERVAL, "Dhuhr", false);
                    utils.resetAdhanAlarm(7708, prayerToMillis(asr) - INTERVAL, prayerToMillisNextDay(asr) - INTERVAL, "Asr", false);
                    utils.resetAdhanAlarm(7709, prayerToMillis(maghrib) - INTERVAL, prayerToMillisNextDay(maghrib) - INTERVAL, "Maghrib", false);
                    utils.resetAdhanAlarm(7710, prayerToMillis(isha) - INTERVAL, prayerToMillisNextDay(isha) - INTERVAL, "Isha", false);



                    isNextDay = false;
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Prayer> call, Throwable t) {
                if (t.getMessage() != null)
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private int getHours(String prayerTime) {
        String result = prayerTime.substring(0, prayerTime.indexOf(":"));
        return Integer.parseInt(result);
    }

    private int getMinutes(String prayerTime) {
        String result = prayerTime.substring(prayerTime.indexOf(":") + 1);
        return Integer.parseInt(result);
    }

    private long prayerToMillis(String prayerTime) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY, getHours(prayerTime));
        calendar.set(Calendar.MINUTE, getMinutes(prayerTime));
        calendar.set(Calendar.SECOND, 0);
        Log.d("llllll", "ptm: " + calendar.getTime());
        return calendar.getTimeInMillis();
    }

    private long prayerToMillisNextDay(String prayerTime) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, getHours(prayerTime));
        calendar.set(Calendar.MINUTE, getMinutes(prayerTime));
        calendar.set(Calendar.SECOND, 0);
        Log.d("TAG", "prayerToMillis: " + calendar.getTime().toString());
        return calendar.getTimeInMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Counter.stop();
    }

    private long now() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Log.d("llllll", "now: " + calendar.getTime());
        return calendar.getTimeInMillis();
    }

}
