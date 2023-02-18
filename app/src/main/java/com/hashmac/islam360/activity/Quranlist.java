package com.hashmac.islam360.activity;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import com.hashmac.islam360.R;
import com.hashmac.islam360.utilities.Data;
import com.hashmac.islam360.adapter.QuranListAdapter;


public class Quranlist extends AppCompatActivity {

    ArrayList<Data> arraylist = new ArrayList<>();
//    private InterstitialAd mInterstitialAd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quranlist);

/*        AdView adView = new AdView(this);
        adView.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));
        adView.setAdSize(AdSize.BANNER);

        RelativeLayout layout = findViewById(R.id.layAdsQuranList);
        layout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

 */

        RecyclerView recyclerview = findViewById(R.id.listviewid);

        String[] quranList = {getString(R.string.Sura1), getString(R.string.Sura2), getString(R.string.Sura3), getString(R.string.Sura4), getString(R.string.Sura5), getString(R.string.Sura6), getString(R.string.Sura7)
                , getString(R.string.Sura8), getString(R.string.Sura9), getString(R.string.Sura10)
                , getString(R.string.Sura11), getString(R.string.Sura12), getString(R.string.Sura13), getString(R.string.Sura14), getString(R.string.Sura15), getString(R.string.Sura16), getString(R.string.Sura17)
                , getString(R.string.Sura18), getString(R.string.Sura19), getString(R.string.Sura20)
                , getString(R.string.Sura21), getString(R.string.Sura22), getString(R.string.Sura23), getString(R.string.Sura24), getString(R.string.Sura25), getString(R.string.Sura26), getString(R.string.Sura27)
                , getString(R.string.Sura28), getString(R.string.Sura29), getString(R.string.Sura30)
                , getString(R.string.Sura31), getString(R.string.Sura32), getString(R.string.Sura33), getString(R.string.Sura34), getString(R.string.Sura35), getString(R.string.Sura36), getString(R.string.Sura37)
                , getString(R.string.Sura38), getString(R.string.Sura39), getString(R.string.Sura40)
                , getString(R.string.Sura41), getString(R.string.Sura42), getString(R.string.Sura43), getString(R.string.Sura44), getString(R.string.Sura45), getString(R.string.Sura46), getString(R.string.Sura47)
                , getString(R.string.Sura48), getString(R.string.Sura49), getString(R.string.Sura50)
                , getString(R.string.Sura51), getString(R.string.Sura52), getString(R.string.Sura53), getString(R.string.Sura54), getString(R.string.Sura55), getString(R.string.Sura56), getString(R.string.Sura57)
                , getString(R.string.Sura58), getString(R.string.Sura59), getString(R.string.Sura60)
                , getString(R.string.Sura61), getString(R.string.Sura62), getString(R.string.Sura63), getString(R.string.Sura64), getString(R.string.Sura65), getString(R.string.Sura66), getString(R.string.Sura67)
                , getString(R.string.Sura68), getString(R.string.Sura69), getString(R.string.Sura70)
                , getString(R.string.Sura71), getString(R.string.Sura72), getString(R.string.Sura73), getString(R.string.Sura74), getString(R.string.Sura75), getString(R.string.Sura76), getString(R.string.Sura77)
                , getString(R.string.Sura78), getString(R.string.Sura79), getString(R.string.Sura80)
                , getString(R.string.Sura81), getString(R.string.Sura82), getString(R.string.Sura83), getString(R.string.Sura84), getString(R.string.Sura85), getString(R.string.Sura86), getString(R.string.Sura87)
                , getString(R.string.Sura88), getString(R.string.Sura89), getString(R.string.Sura90)
                , getString(R.string.Sura91), getString(R.string.Sura92), getString(R.string.Sura93), getString(R.string.Sura94), getString(R.string.Sura95), getString(R.string.Sura96), getString(R.string.Sura97)
                , getString(R.string.Sura98), getString(R.string.Sura99), getString(R.string.Sura100)
                , getString(R.string.Sura101), getString(R.string.Sura102), getString(R.string.Sura103), getString(R.string.Sura104), getString(R.string.Sura105), getString(R.string.Sura106), getString(R.string.Sura107)
                , getString(R.string.Sura108), getString(R.string.Sura109), getString(R.string.Sura110)
                , getString(R.string.Sura111), getString(R.string.Sura112), getString(R.string.Sura113), getString(R.string.Sura114)};


        String[] quranLinks = {getString(R.string.Link1), getString(R.string.Link2), getString(R.string.Link3), getString(R.string.Link4), getString(R.string.Link5), getString(R.string.Link6), getString(R.string.Link7)
                , getString(R.string.Link8), getString(R.string.Link9), getString(R.string.Link10)
                , getString(R.string.Link11), getString(R.string.Link12), getString(R.string.Link13), getString(R.string.Link14), getString(R.string.Link15), getString(R.string.Link16), getString(R.string.Link17)
                , getString(R.string.Link18), getString(R.string.Link19), getString(R.string.Link20)
                , getString(R.string.Link21), getString(R.string.Link22), getString(R.string.Link23), getString(R.string.Link24), getString(R.string.Link25), getString(R.string.Link26), getString(R.string.Link27)
                , getString(R.string.Link28), getString(R.string.Link29), getString(R.string.Link30)
                , getString(R.string.Link31), getString(R.string.Link32), getString(R.string.Link33), getString(R.string.Link34), getString(R.string.Link35), getString(R.string.Link36), getString(R.string.Link37)
                , getString(R.string.Link38), getString(R.string.Link39), getString(R.string.Link40)
                , getString(R.string.Link41), getString(R.string.Link42), getString(R.string.Link43), getString(R.string.Link44), getString(R.string.Link45), getString(R.string.Link46), getString(R.string.Link47)
                , getString(R.string.Link48), getString(R.string.Link49), getString(R.string.Link50)
                , getString(R.string.Link51), getString(R.string.Link52), getString(R.string.Link53), getString(R.string.Link54), getString(R.string.Link55), getString(R.string.Link56), getString(R.string.Link57)
                , getString(R.string.Link58), getString(R.string.Link59), getString(R.string.Link60)
                , getString(R.string.Link61), getString(R.string.Link62), getString(R.string.Link63), getString(R.string.Link64), getString(R.string.Link65), getString(R.string.Link66), getString(R.string.Link67)
                , getString(R.string.Link68), getString(R.string.Link69), getString(R.string.Link70)
                , getString(R.string.Link71), getString(R.string.Link72), getString(R.string.Link73), getString(R.string.Link74), getString(R.string.Link75), getString(R.string.Link76), getString(R.string.Link77)
                , getString(R.string.Link78), getString(R.string.Link79), getString(R.string.Link80)
                , getString(R.string.Link81), getString(R.string.Link82), getString(R.string.Link83), getString(R.string.Link84), getString(R.string.Link85), getString(R.string.Link86), getString(R.string.Link87)
                , getString(R.string.Link88), getString(R.string.Link89), getString(R.string.Link90)
                , getString(R.string.Link91), getString(R.string.Link92), getString(R.string.Link93), getString(R.string.Link94), getString(R.string.Link95), getString(R.string.Link96), getString(R.string.Link97)
                , getString(R.string.Link98), getString(R.string.Link99), getString(R.string.Link100)
                , getString(R.string.Link101), getString(R.string.Link102), getString(R.string.Link103), getString(R.string.Link104), getString(R.string.Link105), getString(R.string.Link106), getString(R.string.Link107)
                , getString(R.string.Link108), getString(R.string.Link109), getString(R.string.Link110)
                , getString(R.string.Link111), getString(R.string.Link112), getString(R.string.Link113), getString(R.string.Link114)};


        for (int i = 0; i < quranList.length; i++) {
            Data quransurahlist = new Data(quranList[i], quranLinks[i]);
            arraylist.add(quransurahlist);
        }

        QuranListAdapter quranAdapter = new QuranListAdapter(arraylist, Quranlist.this);
        LinearLayoutManager manager = new LinearLayoutManager(this,  RecyclerView.VERTICAL, false);

        recyclerview.setAdapter(quranAdapter);
        recyclerview.setLayoutManager(manager);


/*        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        AdRequest request = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(request);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });

 */

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        } else {
            finish();
//        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            if (mInterstitialAd.isLoaded()) {
//                mInterstitialAd.show();
//            } else {
                finish();
 //           }
        }
        return super.onOptionsItemSelected(item);
    }


}
