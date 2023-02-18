package com.hashmac.islam360.adapter;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hashmac.islam360.model.Category;
import com.hashmac.islam360.R;

public class CategoriesListAdapter extends ArrayAdapter<Category> {
    Context context;
    int layoutResourceId;
    List<Category> data;

    public CategoriesListAdapter(Context context, int layoutResourceId,
                                 List<Category> data) {
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
            holder.txtName = (TextView) row.findViewById(R.id.CatName);
            holder.txtCounter = (TextView) row.findViewById(R.id.txtCounter);
            row.setTag(holder);
        } else {
            holder = (ImageHolder) row.getTag();
        }

        Category picture = data.get(position);
        holder.txtName.setText(picture.getName());
//        holder.txtCounter.setText("   " + picture.getCount() + "   ");
        holder.txtCounter.setText(String.valueOf(position+1));

/*        boolean isExist = false;
        AssetManager assetManager = context.getAssets();
        InputStream imageStream = null;
        try {
            imageStream = assetManager.open("categories/"+picture.getFileName()+".png");

            isExist =true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        RoundImage roundedImage;
        if (isExist){
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            roundedImage = new RoundImage(theImage);
            holder.imgCat.setImageDrawable(roundedImage);
        }
        else {
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(),R.mipmap.uncategorized);
            roundedImage = new RoundImage(bm);
            holder.imgCat.setImageDrawable(roundedImage);
        }

 */

        return row;
    }

    static class ImageHolder {
        TextView txtCounter;
        TextView txtName;

    }
}
