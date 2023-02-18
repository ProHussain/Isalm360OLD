package com.hashmac.islam360.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hashmac.islam360.R;
import com.hashmac.islam360.interfaces.TasbehDialogIListener;
import com.hashmac.islam360.model.TasbeehModel;
import com.hashmac.islam360.room.TasbeehRepository;
import com.hashmac.islam360.utilities.AddTasbehDialog;

import java.util.ArrayList;
import java.util.List;

public class TasbehActivity extends AppCompatActivity implements TasbehDialogIListener {
    FloatingActionButton addTasbeh;
    private TextView tasbehName, tasbehTarget, tasbehCounter;
    ProgressBar progressBar;
    CardView tasberPlus;
    int counter = 0, target = 0;
    ImageView imgAdd, imgSub;
    private SharedPreferences preferences;
    private Editor prefEdit;
    TasbeehRepository repository;
    private List<TasbeehModel> tasbeehModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasbeh);
        repository = new TasbeehRepository(getApplication());
        addTasbeh = findViewById(R.id.addTasbehDialog);
        tasbehName = findViewById(R.id.tasbehName);
        tasbehCounter = findViewById(R.id.tasbehCounter);
        tasbehTarget = findViewById(R.id.tasbehTarget);
        tasberPlus = findViewById(R.id.tasbehPlus);
        progressBar = findViewById(R.id.progressBar);
        imgAdd = findViewById(R.id.imgCounterPlus);
        imgSub = findViewById(R.id.imgCounterMinus);
        preferences = getApplicationContext().getSharedPreferences("tasbeeh",MODE_PRIVATE);
        prefEdit = preferences.edit();
        tasbeehModelList = new ArrayList<>();
        if (!preferences.getBoolean("dataAdd",false)) {
            AddData();
        } else {
            tasbeehModelList = repository.getAll();
            Log.e("Size",tasbeehModelList.size()+"");
        }

        if (preferences.getBoolean("isRun",false)) {
            String zikr = preferences.getString("Tasbeeh","Ya Allah");
            int cnt = preferences.getInt("Cnt",0);
            int trg = preferences.getInt("Trg",33);
            SetData(zikr,trg,cnt);
        }
        addTasbeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTasbehDialog dialog = new AddTasbehDialog(TasbehActivity.this,tasbeehModelList);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });

        tasberPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TasbehPlus();
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TasbehPlus();
            }
        });

        imgSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter < target && counter > 0){
                    counter--;
                    progressBar.setProgress(counter);
                    tasbehCounter.setText(String.valueOf(counter));
                }
            }
        });

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void TasbehPlus() {
        counter++;
        if (counter >= target && target > 0) {
            progressBar.setProgress(target);
//                    tasberPlus.setClickable(false);
            AlertDialog.Builder builder = new AlertDialog.Builder(TasbehActivity.this);
            builder.setTitle("Congratulations!")
                    .setMessage("Your Tasbeeh target completed")
                    .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SetData(tasbehName.getText().toString(),target,0);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SetData("Select your Tasbeeh",0,0);
                            tasberPlus.setEnabled(false);
                        }
                    }).show();
        } else if (counter < target && target> 0){
            progressBar.setProgress(counter);
            tasbehCounter.setText(String.valueOf(counter));
        }
    }

    private void AddData() {
        tasbeehModelList.add(new TasbeehModel("Allah hu Akbar"));
        tasbeehModelList.add(new TasbeehModel("Subhan Allah"));
        tasbeehModelList.add(new TasbeehModel("Allhumdulillah"));
        tasbeehModelList.add(new TasbeehModel("Ya Allah"));
        tasbeehModelList.add(new TasbeehModel("Ya Hayyu Ya Kayyum"));
        repository.deleteAllTasbeehs();
        for (TasbeehModel model:tasbeehModelList) {
            repository.insert(model);
        }
        prefEdit.putBoolean("dataAdd",true).commit();
    }

    @Override
    public void SetTasbeeh(String zikar, int target, boolean addList) {
        SetData(zikar,target,0);
        if (addList) {
            TasbeehModel insert = new TasbeehModel(zikar);
            tasbeehModelList.add(insert);
            repository.insert(insert);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (counter > 0 && counter < target) {
            prefEdit.putString("Tasbeeh", tasbehName.getText().toString());
            prefEdit.putInt("Cnt", counter);
            prefEdit.putInt("Trg", target);
            prefEdit.putBoolean("isRun",true);
            prefEdit.commit();
        } else {
            prefEdit.putBoolean("isRun",false);
            prefEdit.commit();
        }
    }

    private void SetData(String zikar, int target, int counter) {
        tasbehName.setText(zikar);
        tasbehCounter.setText(String.valueOf(counter));
        tasbehTarget.setText(String.valueOf(target));
        this.target = target;
        this.counter = counter;
        progressBar.setMax(target);
        progressBar.setProgress(counter);
    }
}