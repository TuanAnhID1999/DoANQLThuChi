package com.example.tuananh.qlthuchi.java.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.tuananh.qlthuchi.R;
import com.example.tuananh.qlthuchi.java.model.ArrGiaodien;

import java.util.ArrayList;


public class AdapterThuchi extends ArrayAdapter<ArrGiaodien> {
    private Activity a;
    private int id;
    private ArrayList<ArrGiaodien> arr;
    private TextView name, money;
    private Handler h;
    private Animation animation = new AnimationUtils().loadAnimation(getContext(), android.R.anim.slide_in_left);

    public AdapterThuchi(Activity context, int resource, ArrayList<ArrGiaodien> objects) {
        super(context, resource, objects);
        this.a = context;
        this.id = resource;
        this.arr = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater in = a.getLayoutInflater();
        view = in.inflate(id, null);
        name = (TextView) view.findViewById(R.id.txtGiaodienName);
        money = (TextView) view.findViewById(R.id.txtGiaodienMoney);
        if (arr.get(position).name.equals("Số dư")) {
            ImageView image = (ImageView) view.findViewById(R.id.imgGiaodienImage);
            image.setImageResource(R.mipmap.calculator_icon);
            money.setBackgroundResource(R.drawable.giaodien_background_money);
            money.setTextColor(Color.WHITE);
        }
        name.setText(arr.get(position).name);
        money.setText("" + arr.get(position).money);
        return view;
    }
}
