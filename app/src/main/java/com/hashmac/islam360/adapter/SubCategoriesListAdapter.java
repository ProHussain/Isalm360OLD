package com.hashmac.islam360.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hashmac.islam360.model.Azkar;
import com.hashmac.islam360.R;


public class SubCategoriesListAdapter extends ArrayAdapter<Azkar> {

    Context context;
    int layoutResourceId;
    List<Azkar> data;

    public SubCategoriesListAdapter(Context context, int layoutResourceId,
                                    List<Azkar> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;

        ImageHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ImageHolder();
            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.imgAuth = (ImageView) row.findViewById(R.id.imgAuth);
            holder.txtCounter = (TextView) row.findViewById(R.id.AuthCounter);
            Typeface font = Typeface.createFromAsset(context.getAssets(),
                    "fonts/Roboto-Light.ttf");
            holder.txtName.setTypeface(font);
            row.setTag(holder);
        } else {
            holder = (ImageHolder) row.getTag();
        }

        Azkar picture = data.get(position);
        holder.txtName.setText(picture.getName());
        holder.txtCounter.setText("   " + picture.getCount() + "   ");
        return row;
    }

    static class ImageHolder {
        TextView txtCounter;
        ImageView imgAuth;
        TextView txtName;

    }
}
