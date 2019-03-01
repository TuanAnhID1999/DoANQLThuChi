package com.example.tuananh.qlthuchi.java.fragment_page;

/**
 * Created by longdg on 12/12/2015.
 */
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.example.tuananh.qlthuchi.R;
import com.example.tuananh.qlthuchi.java.adapter.AdapterGridview;
import com.example.tuananh.qlthuchi.java.adapter.AdapterThuchi;
import com.example.tuananh.qlthuchi.java.com.example.longdg.quanlythuchi.GiaodichActivity;
import com.example.tuananh.qlthuchi.java.model.ArrGiaodien;
import com.example.tuananh.qlthuchi.java.model.ArrNavigation;

import java.util.ArrayList;
import java.util.Calendar;


public class ThuchiFragment extends Fragment {
    private String matk, ngaythang;
    private Activity a;
    private SQLiteDatabase data;
    private ListView lvThuchi;
    private Calendar today;
    private ArrayList<ArrGiaodien> arrThuchi;
    private AdapterThuchi adapterThuchi;
    private ArrayList<ArrNavigation> arrGridview;
    private GridView gridview;
    private AdapterGridview adapterGridview;
    private int thang, nam;
    public ThuchiFragment(String matk) {
        this.matk = matk;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_thuchi, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        a = getActivity();
        data = a.openOrCreateDatabase("data.db", a.MODE_PRIVATE, null);
        today = Calendar.getInstance();
        thang = today.get(Calendar.MONTH) + 1;
        nam = today.get(Calendar.YEAR);
        setListview();
        setGridview();
    }

    public void setListview() {
        arrThuchi = new ArrayList<ArrGiaodien>();
        arrThuchi.add(new ArrGiaodien(R.mipmap.gift_box_icon, "Tiền mặt", 0));
        arrThuchi.add(new ArrGiaodien(R.mipmap.gift_box_icon, "Tiết kiệm", 0));
        arrThuchi.add(new ArrGiaodien(R.mipmap.gift_box_icon, "Thẻ tín dụng", 0));
        arrThuchi.add(new ArrGiaodien(R.mipmap.gift_box_icon, "Số dư", 0));
        lvThuchi = (ListView) a.findViewById(R.id.lvThuchi);
        adapterThuchi = new AdapterThuchi(getActivity(), R.layout.activity_giaodien_item, arrThuchi);
        lvThuchi.setAdapter(adapterThuchi);
    }

    public void setToday() {
        ngaythang = today.get(Calendar.DAY_OF_MONTH) + "/" + thang + "/" + nam;
        setArrThuchi(ngaythang, "is not null");
    }

    public void setArrThuchi(String date, String week) {
        for (int i = 0; i < 4; i++) {
            arrThuchi.get(i).money = 0;
        }
        Cursor c = data.rawQuery("select loaitk, sum(sotien) from tblthuchi " +
                "inner join tblphannhom on tblthuchi.manhom = tblphannhom.manhom " +
                "where ngay like '%" + date + "%' and tuan " + week + " and  mataikhoan = '" + matk + "' group by loaitk", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            if (c.getString(c.getColumnIndex("loaitk")).equals("Tiền mặt")) {
                arrThuchi.get(0).money = c.getInt(c.getColumnIndex("sum(sotien)"));
            } else if (c.getString(c.getColumnIndex("loaitk")).equals("Tiết kiệm")) {
                arrThuchi.get(1).money = c.getInt(c.getColumnIndex("sum(sotien)"));
            } else if (c.getString(c.getColumnIndex("loaitk")).equals("Thẻ tín dụng")) {
                arrThuchi.get(2).money = c.getInt(c.getColumnIndex("sum(sotien)"));
            }
            c.moveToNext();
        }
        arrThuchi.get(3).money = arrThuchi.get(0).money + arrThuchi.get(1).money + arrThuchi.get(2).money;
        adapterThuchi.notifyDataSetChanged();
    }

    public void setGridview() {
        gridview = (GridView) a.findViewById(R.id.gvThuchi);
        arrGridview = new ArrayList<ArrNavigation>();
        arrGridview.add(new ArrNavigation(R.mipmap.deal_icon_red, "Giao dịch"));
        arrGridview.add(new ArrNavigation(R.mipmap.calendar_icon_red, "Hôm nay"));
        arrGridview.add(new ArrNavigation(R.mipmap.calendar_icon_red, "Tuần này"));
        arrGridview.add(new ArrNavigation(R.mipmap.calendar_icon_red, "Tháng này"));
        arrGridview.add(new ArrNavigation(R.mipmap.calendar_icon_red, "Năm nay"));
        arrGridview.add(new ArrNavigation(R.mipmap.about_us_icon_red, "Giới thiệu"));
        adapterGridview = new AdapterGridview(getActivity(), arrGridview);
        gridview.setAdapter(adapterGridview);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eventGridview(position);
            }
        });
    }

    public void eventGridview(int so) {
        switch (so) {
            case 0:
                Intent intent = new Intent(getActivity(), GiaodichActivity.class);
                intent.putExtra("matk", matk);
                startActivity(intent);
                break;
            case 1:
                setToday();
                break;
            case 2:
                String tuan = "= '" + today.get(Calendar.WEEK_OF_MONTH) + "'";
                ngaythang = thang + "/" + nam;
                setArrThuchi(ngaythang, tuan);
                break;
            case 3:
                ngaythang = thang + "/" + nam;
                setArrThuchi(ngaythang, "is not null");
                break;
            case 4:
                ngaythang = "" + nam;
                setArrThuchi(ngaythang, "is not null");
                break;
            case 5:
                Dialog d = new Dialog(getActivity());
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.activity_aboutus);
                d.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                d.show();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setToday();
    }
}

