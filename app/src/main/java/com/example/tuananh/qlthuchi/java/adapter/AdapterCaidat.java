package com.example.tuananh.qlthuchi.java.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.tuananh.qlthuchi.R;
import com.example.tuananh.qlthuchi.java.model.ArrCaidat;

import java.util.ArrayList;


public class AdapterCaidat extends ArrayAdapter<ArrCaidat> {
    private Activity activity;
    private int id;
    private ArrayList<ArrCaidat> arr;
    TextView name, money;

    public AdapterCaidat(Activity context, int resource, ArrayList<ArrCaidat> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.id = resource;
        this.arr = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater in = activity.getLayoutInflater();
        view = in.inflate(id, null);
        if (arr.size()>0&&position>=0) {
            name = (TextView) view.findViewById(R.id.txtThongkeName);
            money = (TextView) view.findViewById(R.id.txtThongkeMoney);
            name.setText(arr.get(position).tenhom);
            money.setText(arr.get(position).tenkhoan);
        }
        return view;
    }
}
