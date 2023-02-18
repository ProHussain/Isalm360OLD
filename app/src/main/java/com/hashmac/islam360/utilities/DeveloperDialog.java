package com.hashmac.islam360.utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hashmac.islam360.R;

public class DeveloperDialog extends Dialog {
    Context context;
    public DeveloperDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer_dialog);

        ImageView fiverr = findViewById(R.id.fiverr);
        ImageView upwork = findViewById(R.id.upwork);
        ImageView wab = findViewById(R.id.wab);

        fiverr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fiverr.com/pro_hussain"));
                context.startActivity(browserIntent);
            }
        });

        upwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.upwork.com/workwith/ghulamhussain22"));
                context.startActivity(browserIntent);
            }
        });

        wab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "+923016907146";
                Uri uri = Uri.parse("smsto:" + number);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                context.startActivity(Intent.createChooser(i, "Chooser"));
            }
        });
    }
}
