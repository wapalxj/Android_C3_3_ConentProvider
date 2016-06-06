package com.c4_hm_phone_contacts2;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import domain.ContactInfo;
import domain.ContactsUtils;

public class MainActivity extends AppCompatActivity {
    private Button btn_query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        btn_query= (Button) findViewById(R.id.btn_query);
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ContactInfo> list= ContactsUtils.displayContaxts(MainActivity.this);
                for (ContactInfo info:list){
                    Log.i("rrrrrrrrrrrrrrrr",info.toString());
                }
            }

        });
    }
}
