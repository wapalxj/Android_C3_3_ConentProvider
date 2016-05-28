package com.c4_hm_01_contentprovider;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import db.BankDBOpenHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //通常下面2行代码都写在ContentProvider的onCreate()中
        BankDBOpenHelper helper=new BankDBOpenHelper(MainActivity.this);
        helper.getWritableDatabase();

    }
}
