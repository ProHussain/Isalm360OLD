package com.hashmac.islam360.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.hashmac.islam360.R;
import com.hashmac.islam360.activity.QuranPlayActivity;
import com.hashmac.islam360.utilities.Data;

public class QuranListAdapter extends RecyclerView.Adapter<QuranListAdapter.ViewHolder> {

    private List<Data> list;
    private Context context;

    public QuranListAdapter(List<Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.quran_listview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView textView = holder.view.findViewById(R.id.titleid);
        TextView positiont = holder.view.findViewById(R.id.position);
        textView.setText(list.get(position).getSubject());
        positiont.setText(String.valueOf(position + 1));
        holder.view.setOnClickListener(v -> {
            Intent i = new Intent(context, QuranPlayActivity.class);
            i.putExtra("name", list.get(position).getSubject());
            i.putExtra("url", list.get(position).getLink());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View view;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
        }
    }
}
