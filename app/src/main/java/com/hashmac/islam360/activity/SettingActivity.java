package com.hashmac.islam360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hashmac.islam360.R;
import com.hashmac.islam360.utilities.DeveloperDialog;
import com.hashmac.islam360.utilities.PrefManager;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        PrefManager prefManager = new PrefManager(SettingActivity.this);

        TextView share = findViewById(R.id.txt_share);
        TextView rate = findViewById(R.id.txt_rate);
        TextView privacy = findViewById(R.id.txt_privacy);
        TextView terms = findViewById(R.id.txt_terms);
        TextView developer = findViewById(R.id.txt_developer);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent("android.intent.action.SEND");
                share.setType("text/plain");
                share.putExtra("android.intent.extra.SUBJECT", getString(R.string.app_name));
                share.putExtra("android.intent.extra.TEXT", "I like to share a ads free Muslim App with you, download it from below link \n https://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(share, "Choose"));
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
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

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://islam360.hashmac.com/islam360privacy.html"));
                startActivity(browserIntent);
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://islam360.hashmac.com/islam360terms.html"));
                startActivity(browserIntent);
            }
        });

        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeveloperDialog dialog = new DeveloperDialog(SettingActivity.this);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });

        SwitchCompat notificationSwitch = findViewById(R.id.notification_switch);
        SwitchCompat soundSwitch = findViewById(R.id.sound_switch);
        SwitchCompat vibrationSwitch = findViewById(R.id.vibration_switch);

        notificationSwitch.setChecked(prefManager.getPushNotification());
        soundSwitch.setChecked(prefManager.getSound());
        vibrationSwitch.setChecked(prefManager.getVibration());

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prefManager.setPushNotification(b);
            }
        });

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prefManager.setSound(b);
            }
        });

        vibrationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prefManager.setVibration(b);
            }
        });

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}