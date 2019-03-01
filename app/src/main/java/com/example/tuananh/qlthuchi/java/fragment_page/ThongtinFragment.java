package com.example.tuananh.qlthuchi.java.fragment_page;

/**
 * Created by longdg on 12/12/2015.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.tuananh.qlthuchi.R;
import com.example.tuananh.qlthuchi.java.adapter.AdapterThongtin;
import com.example.tuananh.qlthuchi.java.model.ArrThongtin;

import java.util.ArrayList;


public class ThongtinFragment extends Fragment {
    private SQLiteDatabase data;
    private String ngay, matk;
    private Activity a;
    private ArrayList<ArrThongtin> arr;
    private ListView lv;
    private AdapterThongtin adapterThongtin;

    public ThongtinFragment(String ngay, String matk) {
        this.ngay = ngay;
        this.matk = matk;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_thongtin_listview, container, false);
        lv = (ListView) v.findViewById(R.id.lvThongtin);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        a = getActivity();
        data = a.openOrCreateDatabase("data.db", a.MODE_PRIVATE, null);
        arr = new ArrayList<ArrThongtin>();
        adapterThongtin = new AdapterThongtin(getActivity(), R.layout.activity_thongtin_item, arr);
        lv.setAdapter(adapterThongtin);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteArr(position);
                return false;
            }
        });
        setArr();
    }

    public void deleteArr(final int so) {
        AlertDialog.Builder d = new AlertDialog.Builder(getActivity());
        d.setTitle(arr.get(so).nhom);
        d.setMessage("Bạn muốn xóa giao dịch này?");
        d.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.rawQuery("delete from tblgiaodich where magiaodich = '" + arr.get(so).ma + "'", null).moveToFirst();
                data.rawQuery("delete from tblthuchi where mathuchi = '" + arr.get(so).ma + "'", null).moveToFirst();
                setArr();
            }
        });
        d.show();
    }

    public void setArr() {
        arr.clear();
        Cursor c = data.rawQuery("select tblthuchi.mathuchi, ngay, gio, sotien, tennhom, loaitk from tblgiaodich " +
                "inner join tblthuchi on tblgiaodich.mathuchi = tblthuchi.mathuchi " +
                "inner join tblphannhom on tblthuchi.manhom = tblphannhom.manhom " +
                "where ngay = '" + ngay + "' and mataikhoan = '" + matk + "'", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            arr.add(new ArrThongtin(c.getInt(c.getColumnIndex("tblthuchi.mathuchi")), c.getString(c.getColumnIndex("ngay")), c.getString(c.getColumnIndex("gio")), c.getInt(c.getColumnIndex("sotien")), c.getString(c.getColumnIndex("tennhom")), c.getString(c.getColumnIndex("loaitk"))));
            c.moveToNext();
        }
        adapterThongtin.notifyDataSetChanged();
    }
}

