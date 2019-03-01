package com.example.tuananh.qlthuchi.java.com.example.longdg.quanlythuchi;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tuananh.qlthuchi.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GiaodichActivity extends AppCompatActivity {
    private SQLiteDatabase data;
    private Cursor c;
    private String matk;
    private String[] arrSpinner;
    private Spinner spTaikhoan, spGiaodich, spPhannhom, spTrangthai;
    private ArrayAdapter<String> adapterSpinner, adapterPhannhom;
    private ArrayList<String> arrPhannhom;
    private Calendar today;
    private EditText txtSotien, txtLydo;
    private Button btnNgay, btnGio;
    private String gio;
    private ArrayList<Integer> arrManhom;
    private Animation animation;
    private SimpleDateFormat simpleDateFormat;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giaodich);
        data = openOrCreateDatabase("data.db", MODE_PRIVATE, null);
        matk = getIntent().getStringExtra("matk");
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_edittext);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = new Date();
        setToolbar();
        setSpinner();
        setWidget();
        setToday();
    }

    public void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGiaodich);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setSpinner() {
        // set Spinner Tài khoản
        arrSpinner = getResources().getStringArray(R.array.taikhoan);
        spTaikhoan = (Spinner) findViewById(R.id.spGiaodichTaikhoan);
        adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTaikhoan.setAdapter(adapterSpinner);
        // set Spinner Trạng thái
        arrSpinner = getResources().getStringArray(R.array.trangthai);
        spTrangthai = (Spinner) findViewById(R.id.spGiaodichTrangthai);
        adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTrangthai.setAdapter(adapterSpinner);
        // set Spinner Phân nhóm
        arrManhom = new ArrayList<Integer>();
        arrPhannhom = new ArrayList<String>();
        spPhannhom = (Spinner) findViewById(R.id.spGiaodichPhannhom);
        adapterPhannhom = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrPhannhom);
        adapterPhannhom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPhannhom.setAdapter(adapterPhannhom);
        // set Spinner Loại giao dịch
        arrSpinner = getResources().getStringArray(R.array.giaodich);
        spGiaodich = (Spinner) findViewById(R.id.spGiaodichGiaodich);
        adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGiaodich.setAdapter(adapterSpinner);
        spGiaodich.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // load danh sach nhóm theo Spinner
                setArrPhannhom();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    // Load danh sách theo nhóm được chọn trong Spinner
    public void setArrPhannhom() {
        arrManhom.clear();
        arrPhannhom.clear();
        c = data.rawQuery("select manhom, tennhom from tblphannhom where tenkhoan = '" + spGiaodich.getSelectedItem().toString() + "' and mataikhoan = '" + matk + "'", null);
        c.moveToFirst();
        while (c.isAfterLast()==false) {
            arrManhom.add(c.getInt(c.getColumnIndex("manhom")));
            arrPhannhom.add(c.getString(c.getColumnIndex("tennhom")));
            c.moveToNext();
        }
        adapterPhannhom.notifyDataSetChanged();
    }
    // set Widget
    public void setWidget() {
        btnNgay = (Button) findViewById(R.id.btnGiaodichNgay);
        btnGio = (Button) findViewById(R.id.btnGiaodichGio);
        txtSotien = (EditText) findViewById(R.id.txtGiaodichSotien);
        txtLydo = (EditText) findViewById(R.id.txtGiaodichLydo);
        today = Calendar.getInstance();
    }
    // Lấy thời gian hiện tại
    public void setToday() {
        int thang = today.get(Calendar.MONTH) + 1;
        gio = today.get(Calendar.HOUR_OF_DAY) + ":" + today.get(Calendar.MINUTE) + ":" + today.get(Calendar.SECOND);
        date = today.getTime();
        btnNgay.setText(simpleDateFormat.format(date));
        btnGio.setText(gio);
    }
    // User chọn ngày tháng
    public void chonngay(View v) {
        final Dialog d = new Dialog(this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.activity_chonngay);
        d.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        d.show();
        final DatePicker datePicker = (DatePicker) d.findViewById(R.id.datePicker);
        Button btnXong = (Button) d.findViewById(R.id.btnChonngayXong);
        btnXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    date = simpleDateFormat.parse(datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                btnNgay.setText(simpleDateFormat.format(date));
                d.cancel();
            }
        });
    }
    // Lấy thời gian hiện tại
    public void hientai(View v) {
        setToday();
    }

    public void luu(View v) {
        String thongbao = "";
        if (txtSotien.getText().toString().equals("")) {
            txtSotien.startAnimation(animation);
            thongbao = "Bạn chưa nhập số tiền";
        } else if (txtLydo.getText().toString().equals("")) {
            txtLydo.startAnimation(animation);
            thongbao = "Bạn chưa nhập lý do";
        } else if (spPhannhom.getChildCount() < 1) {
            thongbao = "Bạn chưa có nhóm nào";
        } else if (saveData()) {
            thongbao = "Lưu thành công";
            txtSotien.setText(null);
            txtLydo.setText(null);
            Intent intent = new Intent(this, ThongtinActivity.class);
            intent.putExtra("matk", matk);
            intent.putExtra("dk", "luu");
            startActivityForResult(intent, 2);
        } else {
            thongbao = "Lưu thất bại";
        }
        Toast.makeText(this, thongbao, Toast.LENGTH_SHORT).show();
    }

    public void luuvathoat(View v) {
        String thongbao = "";
        if (txtSotien.getText().toString().equals("")) {
            txtSotien.startAnimation(animation);
            thongbao = "Bạn chưa nhập số tiền";
        } else if (txtLydo.getText().toString().equals("")) {
            txtLydo.startAnimation(animation);
            thongbao = "Bạn chưa nhập lý do";
        } else if (spPhannhom.getChildCount() < 1) {
            thongbao = "Bạn chưa có nhóm nào";
        } else if (saveData()) {
            txtSotien.setText(null);
            txtLydo.setText(null);
            thongbao = "Lưu thành công";
            finish();
        } else {
            thongbao = "Lưu thất bại";
        }
        Toast.makeText(this, thongbao, Toast.LENGTH_SHORT).show();
    }

    public boolean saveData() {
        // Lấy mã giao dịch
        int ma = 0;
        int sotien = 0;
        c = data.rawQuery("select mathuchi from tblthuchi", null);
        if (c.moveToLast() == true) {
            ma = c.getInt(c.getColumnIndex("mathuchi")) + 1;
        }
        ContentValues values = new ContentValues();
        values.put("mathuchi", ma);
        values.put("loaitk", spTaikhoan.getSelectedItem().toString());
        if (spGiaodich.getSelectedItem().toString().equals("Khoản chi")) {
            sotien = -Integer.parseInt(txtSotien.getText().toString());
        } else {
            sotien = Integer.parseInt(txtSotien.getText().toString());
        }
        values.put("sotien", sotien);
        values.put("tuan", today.get(Calendar.WEEK_OF_MONTH));
        values.put("ngay", simpleDateFormat.format(date));
        values.put("manhom", arrManhom.get(spPhannhom.getSelectedItemPosition()));
        if (data.insert("tblthuchi", null, values) == -1) {
            return false;
        }
        values.clear();
        values.put("magiaodich", ma);
        values.put("lydo", txtLydo.getText().toString());
        values.put("trangthai", spTrangthai.getSelectedItem().toString());
        values.put("gio", gio);
        values.put("mathuchi", ma);
        if (data.insert("tblgiaodich", null, values) == -1) {
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == 0) {
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_giaodich, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

