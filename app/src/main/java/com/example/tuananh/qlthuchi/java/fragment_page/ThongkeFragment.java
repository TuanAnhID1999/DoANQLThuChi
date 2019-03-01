package com.example.tuananh.qlthuchi.java.fragment_page;

/**
 * Created by longdg on 12/12/2015.
 */
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;


import com.example.tuananh.qlthuchi.R;
import com.example.tuananh.qlthuchi.java.adapter.AdapterThongke;
import com.example.tuananh.qlthuchi.java.model.ArrThongke;

import java.util.ArrayList;
import java.util.Calendar;

public class ThongkeFragment extends Fragment {
    private SQLiteDatabase data;
    private Activity a;
    private String matk;
    private ExpandableListView lv;
    private AdapterThongke adapterlv;
    private String[] arrgroup;
    private ArrayList<ArrThongke> arrthu, arrchi;
    private Calendar today;
    private String ngaythang;
    private int thang, nam;

    public ThongkeFragment(String matk) {
        this.matk = matk;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_thongke, container, false);
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
        setSpiner();
    }

    public void setListview() {
        arrgroup = getResources().getStringArray(R.array.thongkegruop);
        arrthu = new ArrayList<ArrThongke>();
        arrchi = new ArrayList<ArrThongke>();
        lv = (ExpandableListView) a.findViewById(R.id.lvThongke);
        adapterlv = new AdapterThongke(this.a, arrgroup, arrthu, arrchi);
        lv.setAdapter(adapterlv);
    }

    public void setSpiner() {
        Spinner spinner = (Spinner) a.findViewById(R.id.spThongke);
        final String[] arr = getResources().getStringArray(R.array.chonngay);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.a, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDate(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setToday() {
        ngaythang = today.get(Calendar.DAY_OF_MONTH) + "/" + thang + "/" + nam;
        setArr(ngaythang, "is not null");
    }

    public void setDate(int so) {
        switch (so) {
            case 0:
                setToday();
                break;
            case 1:
                ngaythang = thang + "/" + nam;
                String tuan = "= '" + today.get(Calendar.WEEK_OF_MONTH) + "'";
                setArr(ngaythang, tuan);
                break;
            case 2:
                ngaythang = thang + "/" + nam;
                setArr(ngaythang, "is not null");
                break;
            case 3:
                setArr(String.valueOf(nam), "is not null");
                break;
        }
    }

    public void setArr(String date, String tuan) {
        arrthu.clear();
        arrchi.clear();
        Cursor c = data.rawQuery("select tennhom, sum(sotien) as tien, tenkhoan from tblthuchi " +
                "inner join tblphannhom on tblthuchi.manhom = tblphannhom.manhom " +
                "where ngay like '%" + date + "%' and tuan " + tuan + " and mataikhoan = '" + matk + "' " +
                "group by tblphannhom.manhom", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            if (c.getString(c.getColumnIndex("tenkhoan")).equals("Khoản thu")) {
                arrthu.add(new ArrThongke(c.getString(c.getColumnIndex("tennhom")), c.getInt(c.getColumnIndex("tien"))));
            } else {
                arrchi.add(new ArrThongke(c.getString(c.getColumnIndex("tennhom")), c.getInt(c.getColumnIndex("tien"))));
            }
            c.moveToNext();
        }
        adapterlv.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        setToday();
    }
}

