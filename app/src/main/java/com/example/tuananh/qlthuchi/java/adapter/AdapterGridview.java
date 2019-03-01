package com.example.tuananh.qlthuchi.java.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.tuananh.qlthuchi.R;
import com.example.tuananh.qlthuchi.java.model.ArrNavigation;

import java.util.ArrayList;



public class AdapterGridview extends BaseAdapter {
    private Activity a;
    private ArrayList<ArrNavigation> arr;
    private ImageView image;
    private TextView name;

    public AdapterGridview(Activity a, ArrayList<ArrNavigation> arr) {
        this.a = a;
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater in = a.getLayoutInflater();
        if (view == null) {
            view = in.inflate(R.layout.activity_gridview_item, parent, false);
        }
        image = (ImageView) view.findViewById(R.id.imgGridviewImgare);
        name = (TextView) view.findViewById(R.id.txtGridviewName);
        image.setImageResource(arr.get(position).image);
        name.setText(arr.get(position).name);
        return view;
    }
}
