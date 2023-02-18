package com.hashmac.islam360.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.hashmac.islam360.R;
import com.hashmac.islam360.model.Reponse;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class QiblaActivity extends AppCompatActivity implements LocationListener, SensorEventListener {

    private GitHubService service;

    private Sensor sensor;
    private SensorManager manager;
    private float currentDegree;
    private double qiblaDegree;
    private TextView degreeText, cityName;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qibla);
        setTitle(getString(R.string.qibla_compass_title));

        degreeText = findViewById(R.id.degree_text);
        cityName = findViewById(R.id.cityname);

        String city = getIntent().getStringExtra("City");
        cityName.setText(city);

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.aladhan.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GitHubService.class);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (ActivityCompat.checkSelfPermission(QiblaActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(QiblaActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }


                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

                        sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

                        //manager.registerListener(getListener(), sensor, SensorManager.SENSOR_DELAY_UI);

                        if (locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                            manager.registerListener(QiblaActivity.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                        } else {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, 4004);
                            Toast.makeText(QiblaActivity.this, getString(R.string.make_sure_your_location_is_enabled), Toast.LENGTH_LONG).show();
                        }

                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            Log.d("TAG", "onCreate: Not null");
                            location.setBearing(0f);
                            onLocationChanged(location);
                        } else {
                            Log.d("TAG", "onCreate: Location is null");
                        }

                    }
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {

                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (manager != null)
            manager.unregisterListener(this, sensor);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        ImageView compass = findViewById(R.id.compass);
        int degree = Math.round(sensorEvent.values[0]);

        degreeText.setText(sensorEvent.values[0] + "°");

        Log.d("TAG", "onSensorChanged: " + sensorEvent.values[0] + "  " + qiblaDegree);
        RotateAnimation rotateAnimation = new RotateAnimation(currentDegree, (float) (-degree + qiblaDegree), Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        findViewById(R.id.compass_layout).setAnimation(rotateAnimation);
        currentDegree = (float) (-degree + qiblaDegree);

        Log.d("TAG", "onSensorChanged: cd  "+ currentDegree);
        if ((float)(qiblaDegree - 3) < degree && degree < (float)(qiblaDegree + 3)) {
            compass.setColorFilter(getResources().getColor(R.color.accent));
        } else {
            compass.setColorFilter(getResources().getColor(R.color.icons));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public interface GitHubService {
        @GET("qibla/{latitude}/{longitude}")
        Call<Reponse> getDegree(@Path("latitude") String latitude,
                                      @Path("longitude") String longitude);
    }

    double Qlati = 21.42243;
    double Qlongi = 39.82624;

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d("TAG", "onLocationChanged: " + location.getLatitude() + "  " + location.getLongitude());
        qiblaDegree = bearing(location.getLatitude(),location.getLongitude(),Qlati,Qlongi);
        if (netCheck(QiblaActivity.this)) {

            service.getDegree(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()))
                    .enqueue(new Callback<Reponse>() {
                        @Override
                        public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                            qiblaDegree = response.body().getData().getDirection();
                        }

                        @Override
                        public void onFailure(Call<Reponse> call, Throwable t) {
                            Log.d("TAG", "onFailure: " + t.getMessage());
                        }
                    });
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    public double bearing(double startLat, double startLng, double endLat, double endLng){
        double longitude1 = startLng;
        double longitude2 = endLng;
        double latitude1 = Math.toRadians(startLat);
        double latitude2 = Math.toRadians(endLat);
        double longDiff= Math.toRadians(longitude2 - longitude1);
        double y= Math.sin(longDiff)* Math.cos(latitude2);
        double x= Math.cos(latitude1)* Math.sin(latitude2)- Math.sin(latitude1)* Math.cos(latitude2)* Math.cos(longDiff);
        return (Math.toDegrees(Math.atan2(y, x))+360)%360;
    }

    public boolean netCheck(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();

        boolean isConnected = nInfo != null && nInfo.isConnectedOrConnecting();
        return isConnected;
    }
}