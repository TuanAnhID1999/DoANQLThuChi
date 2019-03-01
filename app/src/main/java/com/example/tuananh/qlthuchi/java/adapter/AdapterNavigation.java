package com.example.tuananh.qlthuchi.java.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tuananh.qlthuchi.R;


public class AdapterNavigation extends ArrayAdapter<String> {
    private Activity a;
    private int id;
    private String[] arr;
    private TextView name;

    public AdapterNavigation(Activity context, int resource, String[] objects) {
        super(context, resource, objects);
        this.a = context;
        this.id = resource;
        this.arr = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater in = a.getLayoutInflater();
        view = in.inflate(id, null);
        if (arr.length > 0 && position >= 0) {
            name = (TextView) view.findViewById(R.id.txtNavigationName);
            name.setText(arr[position]);
        }
        return view;
    }
}
