package com.example.tuananh.qlthuchi.java.com.example.longdg.quanlythuchi;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.support.design.widget.TabLayout;


import com.example.tuananh.qlthuchi.R;
import com.example.tuananh.qlthuchi.java.adapter.AdapterGiaodien;
import com.example.tuananh.qlthuchi.java.adapter.AdapterNavigation;


public class GiaodienActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tablayout;
    private ViewPager viewPager;
    private AdapterGiaodien adapterGiaodich;
    private DrawerLayout drawerLayout;
    private LinearLayout layout;
    private String[] arr;
    private AdapterNavigation adapterNavigation;
    private ListView listView;
    private String matk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giaodien);
        setResult(0);
        matk = getIntent().getStringExtra("matk");
        setActionBar();
        setTablayout();
        setViewPager();
        addEventTab();
        setDrawer();
    }

    public void setActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarGiaodien);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thu chi");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menus_icon);
    }

    public void setTablayout() {
        tablayout =  findViewById(R.id.tablayout);
        tablayout.addTab(tablayout.newTab().setIcon(R.mipmap.money_white));
        tablayout.addTab(tablayout.newTab().setIcon(R.mipmap.statistical_grey));
        tablayout.addTab(tablayout.newTab().setIcon(R.mipmap.setting_grey));
    }

    public void setViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpagerGiaodien);
        adapterGiaodich = new AdapterGiaodien(getSupportFragmentManager(), matk);
        viewPager.setAdapter(adapterGiaodich);
    }

    public void addEventTab() {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        getSupportActionBar().setTitle("Thu chi");
                        tablayout.getTabAt(0).setIcon(R.mipmap.money_white);
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        getSupportActionBar().setTitle("Thống kê");
                        tablayout.getTabAt(1).setIcon(R.mipmap.statistical_white);
                        viewPager.setCurrentItem(1);
                        break;
                    case 2:
                        getSupportActionBar().setTitle("Cài đặt");
                        tablayout.getTabAt(2).setIcon(R.mipmap.setting_white);
                        viewPager.setCurrentItem(2);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tablayout.getTabAt(0).setIcon(R.mipmap.money_grey);
                        break;
                    case 1:
                        tablayout.getTabAt(1).setIcon(R.mipmap.statistical_grey);
                        break;
                    case 2:
                        tablayout.getTabAt(2).setIcon(R.mipmap.setting_grey);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void setDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        layout = (LinearLayout) findViewById(R.id.layoutGiaodien);
        listView = (ListView) findViewById(R.id.lvDrawer);
        arr = getResources().getStringArray(R.array.navigation);
        adapterNavigation = new AdapterNavigation(this, R.layout.activity_navigation_item, arr);
        listView.setAdapter(adapterNavigation);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addEvent(position);
                drawerLayout.closeDrawer(layout);
            }
        });
    }

    public void addEvent(int so) {
        switch (so) {
            case 0:
                Intent intent = new Intent(this, GiaodichActivity.class);
                intent.putExtra("matk", matk);
                startActivity(intent);
                break;
            case 1:
                Intent intent1 = new Intent(this, ThongtinActivity.class);
                intent1.putExtra("matk", matk);
                intent1.putExtra("dk", "xem");
                startActivity(intent1);
                break;
            case 2:
                viewPager.setCurrentItem(0);
                break;
            case 3:
                viewPager.setCurrentItem(1);
                break;
            case 4:
                viewPager.setCurrentItem(2);
                break;
            case 5:
                SharedPreferences share = getSharedPreferences("taikhoan", MODE_PRIVATE);
                SharedPreferences.Editor edit = share.edit();
                edit.clear();
                edit.commit();
                setResult(1);
                finish();
                break;
            case 6:
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_giaodien, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            if (drawerLayout.isDrawerOpen(layout)) {
                drawerLayout.closeDrawer(layout);
            } else {
                drawerLayout.openDrawer(layout);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

