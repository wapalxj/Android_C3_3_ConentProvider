package com.myapplication;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
                ContentResolver resolver=getContentResolver();
                Uri uri_raw_contacts= Uri.parse("content://com.android.contacts/raw_contacts");
                Uri uri_data= Uri.parse("content://com.android.contacts/data");
                Cursor contacts_cursor=resolver.query(uri_raw_contacts, new String[]{"contact_id"}, null, null, null);
                while (contacts_cursor.moveToNext()){
                    String id=contacts_cursor.getString(0);
                    Log.i("idididididid","id:-----"+id);
                    Cursor dataCursor=resolver.query(uri_data,new String[]{"data1","mimetype"},"raw_contact_id=?",new String[]{id},null);

                    while (dataCursor.moveToNext()){
                        String data=dataCursor.getString(0);
                        String mimetype=dataCursor.getString(1);
                        Log.i("data","data:-----"+data+"---------mimetype:-------"+mimetype);
                    }
                    dataCursor.close();
                    Log.i("----------下一个联系人------","----------下一个联系人------");
                }
                contacts_cursor.close();
            }

        });
    }
}
