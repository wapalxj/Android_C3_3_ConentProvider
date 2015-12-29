package com.c3.vero.c3_02_mycontentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.Preference;

/**
 * Created by vero on 2015/11/27.
 */
public class MyDBOperation {
    private Context mContext=null;
    private SQLiteDatabase mSQLiteDatabase=null;
    private String db_name=null;
    private MySQLiteOpenHelper helper;
    Cursor mCursor=null;

    public MyDBOperation(Context mContext,String db) {
        this.db_name=db;
        this.mContext = mContext;
    }
    public void openOrCreateDatabase(){
        helper =new MySQLiteOpenHelper(mContext,db_name,null,1);
        mSQLiteDatabase=helper.getWritableDatabase();
    }


    public long insert(String table, String nullColumnHack, ContentValues values){
        long i=mSQLiteDatabase.insert(table,nullColumnHack,values);
        return i;
    }

    public int delete(String table, String whereClause, String[] whereArgs){
        int d=mSQLiteDatabase.delete(table, whereClause, whereArgs);
        return d;
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs){
        int u=mSQLiteDatabase.update(table,values,whereClause,whereArgs);
        return u;
    }


    public Cursor query(String table, String[] columns,String selection, String[] selectionArgs, String groupBy,String having, String orderBy){
        mCursor=mSQLiteDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        return mCursor;
    }
}
