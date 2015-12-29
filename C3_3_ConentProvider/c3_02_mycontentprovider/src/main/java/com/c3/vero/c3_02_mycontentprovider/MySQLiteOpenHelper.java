package com.c3.vero.c3_02_mycontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by vero on 2015/11/27.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper{

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,name VARCHAR(20) NOT NULL,age INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE course(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,name VARCHAR(20) NOT NULL)");
        db.execSQL("CREATE TABLE teacher(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,name VARCHAR(20) NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
