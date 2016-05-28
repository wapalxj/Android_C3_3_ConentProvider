package com.c3.vero.c3_3_01_contacts;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> contatcs =new ArrayList<>();
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView =(ListView)findViewById(R.id.contacts_view);
        adapter=new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,contatcs);
        listView.setAdapter(adapter);
        readContacts();
    }

    public void readContacts(){
        Cursor cursor=null;
        cursor=getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null
        );
        while (cursor.moveToNext()){
                String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contatcs.add(name+"\n"+number);
        }
//        adapter.notifyDataSetChanged();
        cursor.close();
    }
}
