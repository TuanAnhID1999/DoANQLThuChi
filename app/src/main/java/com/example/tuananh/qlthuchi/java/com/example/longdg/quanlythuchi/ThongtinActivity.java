package com.example.tuananh.qlthuchi.java.com.example.longdg.quanlythuchi;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.tuananh.qlthuchi.R;
import com.example.tuananh.qlthuchi.java.adapter.AdapterThongtinFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;



public class ThongtinActivity extends AppCompatActivity {
    private SQLiteDatabase data;
    private ImageButton btnThem;
    private ViewPager viewPager;
    private AdapterThongtinFragment adapterThongtin;
    private ArrayList<String> arr;
    private String matk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin);
        data = openOrCreateDatabase("data.db", MODE_PRIVATE, null);
        matk = getIntent().getStringExtra("matk");
        setResult(0);
        setToolbar();
        setViewPager();
    }

    // set Toolbar
    public void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarThongtin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnThem = (ImageButton) findViewById(R.id.btnThongtinThem);
        String dk = getIntent().getStringExtra("dk");
        if (dk.equals("xem")) {
            btnThem.setVisibility(View.GONE);
        } else {
            btnThem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(1);
                    finish();
                }
            });
        }
    }

    // set sự kiện vuốt chọn ngày
    public void setViewPager() {
        // lấy toàn bộ ngày giao dịch
        arr = new ArrayList<String>();
        Cursor c = data.rawQuery("select ngay from tblthuchi " +
                "inner join tblphannhom on tblthuchi.manhom = tblphannhom.manhom " +
                "where mataikhoan = '" + matk + "' group by ngay", null);

        if (c.moveToFirst() != true) {
            arr.add("Nothing");
        } else {
            while (c.isAfterLast() == false) {
                arr.add(c.getString(c.getColumnIndex("ngay")));
                c.moveToNext();
            }
        }

        // sắp xếp thời gian theo ngày tháng
        Collections.sort(arr, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });

        // khai báo viewpager
        viewPager = (ViewPager) findViewById(R.id.viewpagerThongtin);
        adapterThongtin = new AdapterThongtinFragment(getSupportFragmentManager(), arr, matk);
        viewPager.setAdapter(adapterThongtin);
        viewPager.setCurrentItem(arr.size() - 1);
    }
}

