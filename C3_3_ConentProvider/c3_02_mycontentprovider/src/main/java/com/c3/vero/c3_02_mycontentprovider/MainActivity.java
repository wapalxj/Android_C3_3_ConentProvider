package com.c3.vero.c3_02_mycontentprovider;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private MyDBOperation myDBOperation=null;
    private ListView listView=null;
    private SimpleCursorAdapter adapter=null;
    private Cursor mCursor;
    private Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDBOperation=new MyDBOperation(MainActivity.this,"vnix_db");
        myDBOperation.openOrCreateDatabase();
        add=(Button)findViewById(R.id.add);
        add.setOnClickListener(this);
        init();
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.add:
                    Bundle bundle =new Bundle();
                    bundle.putString("operation", "add");
                    onCreateDialog(1,bundle).show();
                break;
            default:
                break;
        }
    }
    public void init(){
        mCursor=query();
        listView=(ListView)findViewById(R.id.listview);
        adapter=new SimpleCursorAdapter(MainActivity.this,
                android.R.layout.simple_list_item_2,mCursor,
                new String[]{"name","age"},new int[]{android.R.id.text1,android.R.id.text2});
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }
    public Cursor query(){
        Cursor cursor=myDBOperation.query("user", new String[]{"_id","name", "age"}, null, null, null, null, null);
        return cursor;
    }


    //用于存储cursor指向记录的id
    String cur_id=null;
    @Nullable
    @Override
    protected Dialog onCreateDialog(int id, final Bundle args) {
        String operation=(String) args.getString("operation");
        int list_po;
        //0是删除
        if (id==0){
            list_po = (int) args.getInt("list_po");
            mCursor.moveToPosition(list_po);
            cur_id = mCursor.getString(0);
            AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this)
                    .setTitle("delete????")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myDBOperation.delete("user","_id=?",new String[]{cur_id});
                            init();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
            return alertDialog;
        }else if (id==1){

            final View dialog_view=getLayoutInflater().inflate(R.layout.dialog,null);
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setView(dialog_view);
            final EditText name=(EditText)dialog_view.findViewById(R.id.username);
            final EditText age=(EditText)dialog_view.findViewById(R.id.age);

            //edit
            if (operation.equals("update")){
                list_po = (int) args.getInt("list_po");
                mCursor.moveToPosition(list_po);
                cur_id = mCursor.getString(0);
                name.setText(mCursor.getString(1));
                age.setText(mCursor.getString(2));
                builder.setTitle("updata")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ContentValues values =new ContentValues();
                                values.put("name",name.getText().toString());
                                values.put("age", age.getText().toString());
                                myDBOperation.update("user", values, "_id=?", new String[]{cur_id});
                                init();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            //add
            }else if (operation.equals("add")){
                    builder.setTitle("add")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ContentValues values =new ContentValues();
                                    values.put("name",name.getText().toString());
                                    values.put("age",age.getText().toString());
                                    myDBOperation.insert("user", null, values);
                                    init();
                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
            }
            AlertDialog dialog=builder.create();
            return dialog;
        }

        return null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int item_id=item.getItemId();//获得menu点击的位置
        //获得listview点击的位置
        AdapterView.AdapterContextMenuInfo menuInfo =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        Bundle bundle =new Bundle();
        bundle.putInt("list_po", menuInfo.position);

        if (item_id==R.id.delete){
            bundle.putString("operation", "del");
            onCreateDialog(0,bundle).show();
        }else if(item_id==R.id.update){
            bundle.putString("operation", "update");
            onCreateDialog(1,bundle).show();
        }

        return super.onContextItemSelected(item);
    }


}
