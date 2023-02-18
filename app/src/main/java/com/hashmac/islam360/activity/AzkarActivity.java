package com.hashmac.islam360.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import com.hashmac.islam360.model.Azkar;
import com.hashmac.islam360.utilities.DataBaseHandler;
import com.hashmac.islam360.R;

public class AzkarActivity extends AppCompatActivity  {


    private String fav;
    private Azkar ziker;
    private DataBaseHandler db;
    private ArrayList<Azkar> myList = new ArrayList<>();
    public static final String USER_LANGUAGES = "user_langu";

    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azkar);
        SharedPreferences preferences = getSharedPreferences("settings", MODE_PRIVATE);
        String userlango = preferences.getString(USER_LANGUAGES , "en");
        Locale locale = new Locale(userlango);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;


        db = new DataBaseHandler(this);
        TextView textAuth = findViewById(R.id.textAuth);
        TextView textQuote = findViewById(R.id.textazkar);
        TextView textQuoteArabic = findViewById(R.id.azkar_arabic);
        scrollView = findViewById(R.id.scrollAzkar);
        ImageButton btnNext = (ImageButton) findViewById(R.id.btn_next);
        ImageButton btnPrevious = (ImageButton) findViewById(R.id.btn_prev);
        textQuote.setTextSize(21);
        textQuoteArabic.setTextSize(21);
        AtomicInteger currentId = new AtomicInteger(getIntent().getExtras().getInt("id"));
        String mode = getIntent().getExtras().getString("mode");

        if (mode == null || mode.equals("") || mode.equals("allAzakers")) {
            myList = (ArrayList<Azkar>) db.getAllAzkar("");
            ziker = myList.get(currentId.get());
        } else {

            switch (mode) {
                case "zikerday":
                    ziker = db.getAzkar(getIntent().getIntExtra("id", 0));
                    btnNext.setVisibility(View.GONE);
                    btnPrevious.setVisibility(View.GONE);
                    break;
                case "ca": {
                    String ca = getIntent().getStringExtra("category");
                    myList = (ArrayList<Azkar>) db.getAzkarByCategory(ca);
                    ziker = myList.get(currentId.get());
                    break;
                }
                case "oc": {
                    String ca = getIntent().getStringExtra("sub");
                    ziker = db.getAzkarBySubcat(ca).get(currentId.get());
                    myList = (ArrayList<Azkar>) db.getAzkarBySubcat(ca);
                    break;
                }
                case "fav":
                    ziker = db.getFavorites().get(currentId.get());
                    myList = (ArrayList<Azkar>) db.getFavorites();
                    break;
            }
        }

        textAuth.setText(ziker.getName());
        textQuoteArabic.setText(ziker.getArabic());
        textQuote.setText(ziker.getAzkar());
        checkPicure();
        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fav = ziker.getFav();

        MenuFun();

    }

    private void MenuFun() {
        findViewById(R.id.copyAzkar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String duaa = ziker.getAzkar() + "- " + ziker.getName();
                copyToClipBoard(duaa);
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.copy_msg),
                        Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.shareAzkar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doShare();
            }
        });
        ImageView fvrt = findViewById(R.id.fvrtAzkar);

        if (fav.equals("0")) {
            fvrt.setImageResource(R.drawable.ic_baseline_favorite_24);
        }
        if (fav.equals("1")) {
            fvrt.setImageResource(R.drawable.ic_baseline_delete_forever_24);
        }


        fvrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ziker.getFav().equals("0")) {
                    ziker.setFav("1");
                    db.updateAzkar(ziker);
                    fvrt.setImageResource(R.drawable.ic_baseline_delete_forever_24);
                } else if (ziker.getFav().equals("1")) {
                    ziker.setFav("0");
                    db.updateAzkar(ziker);
                    fvrt.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
            }
        });
    }

    public void checkPicure(){
        boolean isExist = false;
        InputStream imageStream = null;
        try {
            imageStream = getAssets().open("subcategories/"+ziker.getFileName()+".png");

            isExist =true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void doShare() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getApplicationInfo().name);
        intent.putExtra(Intent.EXTRA_TEXT,
                ziker.getAzkar() + "  - " + ziker.getName());
        AzkarActivity.this.startActivity(Intent.createChooser(intent,
                getResources().getString(R.string.share)));

    }

    @TargetApi(11)
    private void copyToClipBoard(String ziker) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(ziker);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("text", ziker);
            clipboard.setPrimaryClip(clip);
        }
    }
}
