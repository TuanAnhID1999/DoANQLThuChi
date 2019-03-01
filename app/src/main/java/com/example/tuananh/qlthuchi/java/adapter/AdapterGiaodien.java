package com.example.tuananh.qlthuchi.java.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tuananh.qlthuchi.java.fragment_page.CaidatFragment;
import com.example.tuananh.qlthuchi.java.fragment_page.ThongkeFragment;
import com.example.tuananh.qlthuchi.java.fragment_page.ThuchiFragment;


public class AdapterGiaodien extends FragmentPagerAdapter {
    private String matk;

    public AdapterGiaodien(FragmentManager fm, String matk) {
        super(fm);
        this.matk = matk;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new ThuchiFragment(matk);
            case 1:
                return new ThongkeFragment(matk);
            case 2:
                return new CaidatFragment(matk);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
