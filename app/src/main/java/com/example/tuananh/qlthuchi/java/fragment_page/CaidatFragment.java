package com.example.tuananh.qlthuchi.java.fragment_page;

/**
 * Created by longdg on 12/12/2015.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.tuananh.qlthuchi.R;
import com.example.tuananh.qlthuchi.java.adapter.AdapterCaidat;
import com.example.tuananh.qlthuchi.java.model.ArrCaidat;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class CaidatFragment extends Fragment {
    private SQLiteDatabase data;
    private Activity a;
    private String matk;
    private ArrayList<ArrCaidat> arrListview;
    private ListView lvCaidat;
    private AdapterCaidat adapterListview;
    private String[] arrSpinner;
    private Spinner spPhannhom, spThuchi;
    private ArrayAdapter<String> adapterSpinner;
    private EditText txtTennhom;
    private ImageButton btnThem;
    private Animation animation;

    public CaidatFragment(String matk) {
        this.matk = matk;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_caidat, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        a = getActivity();
        data = a.openOrCreateDatabase("data.db", a.MODE_PRIVATE, null);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);
        setListView();
        setSpinner();
        addEvent();
    }

    public void setListView() {
        arrListview = new ArrayList<ArrCaidat>();
        lvCaidat = (ListView) a.findViewById(R.id.lvCaidat);
        adapterListview = new AdapterCaidat(getActivity(), R.layout.activity_thongke_caidat_item, arrListview);
        lvCaidat.setAdapter(adapterListview);
        lvCaidat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteData(arrListview.get(position).tenhom, arrListview.get(position).ma);
                return false;
            }
        });
    }

    public void deleteData(String name, final int ma) {
        AlertDialog.Builder d = new AlertDialog.Builder(getActivity());
        d.setTitle(name);
        d.setMessage("Bạn muốn xóa nhóm này?");
        d.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.rawQuery("delete from tblphannhom where manhom = '" + ma + "'", null).moveToFirst();
                txtTennhom.setText(null);
                setArrListview();
                Toast.makeText(getActivity(), "Xóa nhóm thành công", Toast.LENGTH_SHORT).show();
            }
        });
        d.show();
    }

    public void setSpinner() {
        // set Spinner Khoản thu/chi
        arrSpinner = getResources().getStringArray(R.array.giaodich);
        spThuchi = (Spinner) a.findViewById(R.id.spCaidatThuchi);
        adapterSpinner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spThuchi.setAdapter(adapterSpinner);
        // set Spinner Phân nhóm
        arrSpinner = getResources().getStringArray(R.array.phannhom);
        spPhannhom = (Spinner) a.findViewById(R.id.spCaidatPhannhom);
        adapterSpinner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPhannhom.setAdapter(adapterSpinner);
        spPhannhom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setArrListview();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setArrListview() {
        arrListview.clear();
        String dieukien = "is not null";
        if (spPhannhom.getSelectedItem().toString().equals("Phân nhóm")) {
            dieukien = "is not null";
        } else {
            dieukien = "= '" + spPhannhom.getSelectedItem().toString() + "'";
        }
        Cursor c = data.rawQuery("select manhom, tennhom, tenkhoan from tblphannhom where tenkhoan " + dieukien + " and mataikhoan = '" + matk + "'", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            arrListview.add(new ArrCaidat(c.getInt(c.getColumnIndex("manhom")), c.getString(c.getColumnIndex("tennhom")), c.getString(c.getColumnIndex("tenkhoan"))));
            c.moveToNext();
        }
        adapterListview.notifyDataSetChanged();
    }

    public void addEvent() {
        txtTennhom = (EditText) a.findViewById(R.id.txtCaidatTennhom);
        btnThem = (ImageButton) a.findViewById(R.id.btnCaidatThem);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDate();
            }
        });
    }

    public void saveDate() {
        if (txtTennhom.getText().toString().equals("")) {
            txtTennhom.startAnimation(animation);
            Toast.makeText(getActivity(), "Bạn chưa nhập tên nhóm", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder d = new AlertDialog.Builder(getActivity());
            d.setTitle(txtTennhom.getText().toString());
            d.setMessage("Bạn muốn thêm nhóm này?");
            d.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            d.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int ma = 1;
                    Cursor c = data.rawQuery("select manhom from tblphannhom", null);
                    if (c.moveToLast() == true) {
                        ma = c.getInt(c.getColumnIndex("manhom")) + 1;
                    }
                    String thongbao = "";
                    ContentValues values = new ContentValues();
                    values.put("manhom", ma);
                    values.put("tennhom", txtTennhom.getText().toString());
                    values.put("tenkhoan", spThuchi.getSelectedItem().toString());
                    values.put("mataikhoan", matk);
                    if (data.insert("tblphannhom", null, values) != -1) {
                        thongbao = "Thêm nhóm thành công";
                        txtTennhom.setText(null);
                        setArrListview();
                    } else {
                        thongbao = "Thêm nhóm thất bại";
                    }
                    Toast.makeText(getActivity(), thongbao, Toast.LENGTH_SHORT).show();
                }
            });
            d.show();
        }
    }
}

