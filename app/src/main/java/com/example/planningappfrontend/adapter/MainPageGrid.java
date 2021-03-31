package com.example.planningappfrontend.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.planningappfrontend.R;

import static com.example.planningappfrontend.MainActivity.GRID;
import static com.example.planningappfrontend.MainActivity.pageIcons;
import static com.example.planningappfrontend.MainActivity.pageTitles;


public class MainPageGrid extends BaseAdapter {


    public MainPageGrid() {
    }

    @Override
    public int getCount() {
        return pageTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return pageTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_row, null);
        }

        TextView title = convertView.findViewById(R.id.title_row);
        title.setText(pageTitles[position]);
        ImageView icon = convertView.findViewById(R.id.icon_row);
        Glide.with(parent.getContext()).load(pageIcons[position]).into(icon);

        return convertView;
    }
}
