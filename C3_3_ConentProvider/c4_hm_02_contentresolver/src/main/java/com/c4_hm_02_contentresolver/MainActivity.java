package com.c4_hm_02_contentresolver;
/**
 * 操作gm_01中建立的数据库
 */

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void add(View v){
        Intent intent=new Intent();
        ContentResolver resolver=getContentResolver();
//        Uri uri=Uri.parse("content://com.vero.BankBackDoorProvider/accounts");
        Uri uri=Uri.parse("content://com.vero.BankBackDoorProvider/aaaaAAA");//测试错误的URI
        ContentValues values=new ContentValues();
        resolver.insert(uri,values);

    }
}
