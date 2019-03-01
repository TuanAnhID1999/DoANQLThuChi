package com.example.tuananh.qlthuchi.java.model;

/**
 * Created by longdg on 12/12/2015.
 */
public class ArrThongtin {
    public String date, time, nhom, taikhoan;
    public int money, ma;

    public ArrThongtin(int ma, String date, String time, int money, String nhom, String taikhoan) {
        this.ma = ma;
        this.date = date;
        this.time = time;
        this.money = money;
        this.nhom = nhom;
        this.taikhoan = taikhoan;
    }
}