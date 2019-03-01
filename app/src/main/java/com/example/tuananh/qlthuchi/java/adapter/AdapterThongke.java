package com.example.tuananh.qlthuchi.java.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.tuananh.qlthuchi.R;
import com.example.tuananh.qlthuchi.java.model.ArrThongke;

import java.util.ArrayList;



public class AdapterThongke extends BaseExpandableListAdapter {
    private Context c;
    private String[] arrgroup;
    private ArrayList<ArrThongke> arrthu, arrchi;
    private TextView txtNamegroup, txtNumber, txtNamechild, txtMoneyChild;

    public AdapterThongke(Context c, String[] arrgroup, ArrayList<ArrThongke> arrthu, ArrayList<ArrThongke> arrchi) {
        this.c = c;
        this.arrgroup = arrgroup;
        this.arrthu = arrthu;
        this.arrchi = arrchi;
    }

    @Override
    public int getGroupCount() {
        return arrgroup.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 0) {
            return arrthu.size();
        }
        return arrchi.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(c).inflate(R.layout.activity_thongke_group_item, parent, false);
        }
        txtNamegroup = (TextView) view.findViewById(R.id.txtThongkeGroupName);
        txtNumber = (TextView) view.findViewById(R.id.txtThongkeGroupNumber);
        txtNamegroup.setText(arrgroup[groupPosition]);
        if (groupPosition == 0) {
            txtNumber.setText("" + arrthu.size());
        } else {
            txtNumber.setText("" + arrchi.size());
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(c).inflate(R.layout.activity_thongke_caidat_item, parent, false);
        }
        txtNamechild = (TextView) view.findViewById(R.id.txtThongkeName);
        txtMoneyChild = (TextView) view.findViewById(R.id.txtThongkeMoney);
        if (groupPosition == 0) {
            txtNamechild.setText(arrthu.get(childPosition).name);
            txtMoneyChild.setText("" + arrthu.get(childPosition).money);
            view.setBackgroundColor(Color.parseColor("#EEEEEE"));
        } else {
            txtNamechild.setText(arrchi.get(childPosition).name);
            txtMoneyChild.setText("" + arrchi.get(childPosition).money);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
