package com.example.tuananh.qlthuchi.java.com.example.longdg.quanlythuchi;


import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tuananh.qlthuchi.R;

public class DangkyActivity extends Activity {
    private SQLiteDatabase data;
    private EditText maso, user, pass;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        data = openOrCreateDatabase("data.db", MODE_PRIVATE, null);
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_edittext);
        maso = (EditText)findViewById(R.id.txtNhapmasodangky);
        user = (EditText)findViewById(R.id.txtNhaptendangnhapdangky);
        pass = (EditText)findViewById(R.id.txtNhapmatkhaudangky);
    }

    public void xoatrang(View v) {
        maso.setText(null);
        user.setText(null);
        pass.setText(null);
    }

    public void dangky(View v) {
        boolean mk = true, tk = true;
        String thongbao = "";
        Cursor c = data.rawQuery("select * from tbltaikhoan", null);
        c.moveToFirst();
        while (c.isAfterLast()==false) {
            if (c.getString(c.getColumnIndex("mataikhoan")).equals(maso.getText().toString())) {
                mk = false;
            } else if (c.getString(c.getColumnIndex("tentaikhoan")).equals(user.getText().toString())) {
                tk = false;
            }
            c.moveToNext();
        }
        if (maso.getText().toString().equals("")) {
            thongbao = "Bạn chưa nhập mã số";
            maso.startAnimation(animation);
        } else if (user.getText().toString().equals("")) {
            thongbao = "Bạn chưa nhập username";
            user.startAnimation(animation);
        } else if (pass.getText().toString().equals("")) {
            thongbao = "Bạn chưa nhập password";
            pass.startAnimation(animation);
        } else if (mk==false) {
            thongbao = "Hãy chọn mã số bí mật khác";
            maso.startAnimation(animation);
        } else if (tk==false) {
            thongbao = "Tài khoản đã tồn tại";
            user.startAnimation(animation);
        } else {
            ContentValues values = new ContentValues();
            values.put("mataikhoan", maso.getText().toString());
            values.put("tentaikhoan", user.getText().toString());
            values.put("matkhau", pass.getText().toString());
            if (data.insert("tbltaikhoan", null, values)!=-1) {
                thongbao = "Tạo tài khoản thành công";
                finish();
            } else {
                thongbao = "Không thể tạo tài khoản";
            }
        }
        Toast.makeText(getApplicationContext(), thongbao, Toast.LENGTH_SHORT).show();
    }
}
