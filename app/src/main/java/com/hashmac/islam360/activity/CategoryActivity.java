package com.hashmac.islam360.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import com.hashmac.islam360.adapter.CategoriesListAdapter;
import com.hashmac.islam360.model.Category;
import com.hashmac.islam360.utilities.DataBaseHandler;
import com.hashmac.islam360.R;


public class CategoryActivity extends AppCompatActivity {

    private ArrayList<Category> imageArry = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        DataBaseHandler db = new DataBaseHandler(this);

        List<Category> categories = db.getAllCategories();
        imageArry.addAll(categories);

        CategoriesListAdapter adapter = new CategoriesListAdapter(this, R.layout.category_items,
                imageArry);

        ListView dataList = (ListView) findViewById(R.id.categoryList);
        dataList.setAdapter(adapter);
        dataList.setOnItemClickListener((parent, viewClicked, position, idInDB) -> {

            Category srr = imageArry.get(position);
            Intent i = new Intent(getApplicationContext(),
                    AzkarsActivity.class);
            i.putExtra("category", srr.getName());
            i.putExtra("mode", "ca");
            startActivity(i);

        });

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
/*        AdView adView = new AdView(this);
        adView.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));
        adView.setAdSize(AdSize.BANNER);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layAdsCategories);
        layout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

 */

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return true;
    }

}
