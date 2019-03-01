package com.example.tuananh.qlthuchi.java.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tuananh.qlthuchi.java.fragment_page.ThongtinFragment;

import java.util.ArrayList;



public class AdapterThongtinFragment extends FragmentPagerAdapter {
    private ArrayList<String> arr;
    private String matk;

    public AdapterThongtinFragment(FragmentManager fm, ArrayList<String> arr, String matk) {
        super(fm);
        this.arr = arr;
        this.matk = matk;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ThongtinFragment(arr.get(position), matk);
        return fragment;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arr.get(position);
    }
}
