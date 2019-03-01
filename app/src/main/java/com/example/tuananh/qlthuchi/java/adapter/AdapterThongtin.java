package com.example.tuananh.qlthuchi.java.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.tuananh.qlthuchi.R;
import com.example.tuananh.qlthuchi.java.model.ArrThongtin;

import java.util.ArrayList;



public class AdapterThongtin extends ArrayAdapter<ArrThongtin> {
    private Activity a;
    private int id;
    private ArrayList<ArrThongtin> arr;
    private TextView date, money, nhon, taikhoan;

    public AdapterThongtin(Activity context, int resource, ArrayList<ArrThongtin> objects) {
        super(context, resource, objects);
        this.a = context;
        this.id = resource;
        this.arr = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater in = a.getLayoutInflater();
        view = in.inflate(id, null);
        if (arr.size()>0 && position>=0) {
            date = (TextView) view.findViewById(R.id.txtThongtinDate);
            money = (TextView) view.findViewById(R.id.txtThongtinMoney);
            nhon = (TextView) view.findViewById(R.id.txtThongtinNhom);
            taikhoan = (TextView) view.findViewById(R.id.txtThongtinTaikhoan);
            date.setText(arr.get(position).date + " " + arr.get(position).time);
            money.setText(""+arr.get(position).money);
            nhon.setText(arr.get(position).nhom);
            taikhoan.setText(arr.get(position).taikhoan);
        }
        return view;
    }
}
