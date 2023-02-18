package com.hashmac.islam360.utilities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hashmac.islam360.R;
import com.hashmac.islam360.interfaces.TasbehDialogIListener;
import com.hashmac.islam360.model.TasbeehModel;

import java.util.ArrayList;
import java.util.List;

public class AddTasbehDialog extends Dialog {

    private TextInputEditText edZikar, targetZikar;
    private TextInputLayout edZikarParent;
    private MaterialCheckBox addIntoList;
    private Button okBtn;
    private int totalCounter;
    private boolean isCustom = false;
    private List<String> tasbeehList;

    private TasbehDialogIListener tasbehDialogIListener;

    public AddTasbehDialog(@NonNull Context context, List<TasbeehModel> tasbeehModelList) {
        super(context);
        tasbehDialogIListener = (TasbehDialogIListener) context;
        tasbeehList = new ArrayList<>();
        for (TasbeehModel model:tasbeehModelList) {
            this.tasbeehList.add(model.getTasbeehName());
        }
        tasbeehList.add("Custom");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tasbeh_dialog);

        edZikar = findViewById(R.id.enter_your_zikar);
        edZikarParent = findViewById(R.id.enter_your_zikar_parent);
        addIntoList = findViewById(R.id.add_into_list);
        targetZikar = findViewById(R.id.your_target);
        okBtn = findViewById(R.id.ok_button);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, tasbeehList);
        MaterialAutoCompleteTextView selectZikar = findViewById(R.id.select_your_zikar);
        selectZikar.setAdapter(adapter);
        selectZikar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (tasbeehList.get(i).equalsIgnoreCase("Custom")) {
                    edZikarParent.setVisibility(View.VISIBLE);
                    addIntoList.setVisibility(View.VISIBLE);
                    isCustom = true;
                } else {
                    isCustom = false;
                    edZikarParent.setVisibility(View.GONE);
                    addIntoList.setVisibility(View.GONE);
                }
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String target = targetZikar.getText().toString().trim();
                if (isCustom) {
                    String tasbeh = edZikar.getText().toString().trim();
                    if (!tasbeh.isEmpty() && !target.isEmpty()) {
                        totalCounter = Integer.parseInt(target);
                        if (addIntoList.isChecked()) {
                            tasbeehList.add(tasbeh);
                        }
                        tasbehDialogIListener.SetTasbeeh(tasbeh,totalCounter,true);
                        dismiss();
                    }
                } else {
                    String tasbeh = selectZikar.getText().toString().trim();
                    if (!tasbeh.isEmpty() && !target.isEmpty()) {
                        totalCounter = Integer.parseInt(target);
                        tasbehDialogIListener.SetTasbeeh(tasbeh,totalCounter,false);
                        dismiss();
                    }
                }
            }
        });



    }

}
