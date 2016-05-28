package com.c4_hm_03_sms;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * 短信相关
 * 这个程序用genymotion调测
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void add(View view){
        //插入短信到系统的短信数据库
        ContentResolver resolver=getContentResolver();
        Uri uri=Uri.parse("content://sms");
        ContentValues values=new ContentValues();
        values.put("address","1846805");
        values.put("date",System.currentTimeMillis());
        values.put("type","1");
        values.put("body","hahahah"+ new Random().nextInt());
        Uri uriI=resolver.insert(uri,values);

        Log.i("insert","insert:"+uriI);
    }
    public void delete(View view){
        //删除系统的短信数据库中的短信
        ContentResolver resolver=getContentResolver();
        Uri uri=Uri.parse("content://sms");
        int nums=resolver.delete(uri,"address=?",new String[]{"1846805"});

        Log.i("delete","delete:"+nums);
    }
}
