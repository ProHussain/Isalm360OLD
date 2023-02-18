package com.hashmac.islam360.activity;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

import com.hashmac.islam360.R;


public class AzkarsTimeSettingsActivity extends AppCompatActivity {

    public static final String MIN = "minutes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azkars_time_settings);


        final SharedPreferences sharedPreferences = getSharedPreferences("checkFail1", MODE_PRIVATE);
        int hours = sharedPreferences.getInt(MIN, 5);

        RadioButton radio5M = (RadioButton) findViewById(R.id.Radio_5_m);
        RadioButton radio15M = (RadioButton) findViewById(R.id.Radio_15_m);
        RadioButton radio60M = (RadioButton) findViewById(R.id.Radio_60_m);
        RadioButton radio120M = (RadioButton) findViewById(R.id.Radio_120_m);

        switch (hours) {
            case 5:
                radio5M.setChecked(true);
                break;
            case 15:
                radio15M.setChecked(true);
                break;
            case 60:
                radio60M.setChecked(true);
                break;
            case 120:
                radio120M.setChecked(true);
                break;
            default:
                radio60M.setChecked(true);
                break;
        }


        findViewById(R.id.Radio_5_m).setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(MIN, 5);
            radio15M.setChecked(false);
            radio60M.setChecked(false);
            radio120M.setChecked(false);
            editor.apply();
        });

        findViewById(R.id.Radio_15_m).setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(MIN, 15);
            radio5M.setChecked(false);
            radio60M.setChecked(false);
            radio120M.setChecked(false);
            editor.apply();
        });

        findViewById(R.id.Radio_60_m).setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(MIN, 60);
            radio5M.setChecked(false);
            radio15M.setChecked(false);
            radio120M.setChecked(false);
            editor.apply();

        });
        findViewById(R.id.Radio_120_m).setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(MIN, 120);
            radio5M.setChecked(false);
            radio60M.setChecked(false);
            radio15M.setChecked(false);
            editor.apply();


        });

        findViewById(R.id.donebut).setOnClickListener(view -> finish());

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
