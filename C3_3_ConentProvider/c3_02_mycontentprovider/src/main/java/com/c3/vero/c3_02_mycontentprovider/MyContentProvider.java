package com.c3.vero.c3_02_mycontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import java.util.List;

/**
 * Created by vero on 2015/11/26.
 *
 * 本应用作为server-提供者provider
 *
 */
public class MyContentProvider extends ContentProvider {
    private final static int USER_DIR=1;
    private final static int USER_ITEM=2;
    private final static int COURSE_DIR=3;
    private final static int COURSE_ITEM=4;
    private static UriMatcher matcher;
    private MySQLiteOpenHelper helper;
    private SQLiteDatabase database;

    static {
        matcher =new UriMatcher(0);
        matcher.addURI("vero.demo.myprovider","user",USER_DIR);
        matcher.addURI("vero.demo.myprovider","user/#",USER_ITEM);
        matcher.addURI("vero.demo.myprovider","course",COURSE_DIR);
        matcher.addURI("vero.demo.myprovider","course/#",COURSE_ITEM);
    }
    @Override
    public boolean onCreate() {
        helper=new MySQLiteOpenHelper(getContext(),"vnix_db",null,1);
        database=helper.getReadableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor=null;
        switch (matcher.match(uri)){
            case USER_DIR:
                cursor=database.query("user",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case USER_ITEM:
                List list=uri.getPathSegments();
                String id=(String)list.get(0);
                cursor=database.query("user",projection,selection,new String[]{id},null,null,sortOrder);
                break;
            case COURSE_DIR:
                cursor=database.query("course",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case COURSE_ITEM:
                List list_course=uri.getPathSegments();
                String id_course=(String)list_course.get(0);
                cursor=database.query("course",projection,"_id=?",new String[]{id_course},null,null,sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri mUri =null;
        long id;
        switch (matcher.match(uri)){
            case USER_DIR:
            case USER_ITEM:
                id=database.insert("user",null,values);
                mUri=Uri.parse("content://vero.demo.myprovider/user/"+id);
                break;
            case COURSE_DIR:
            case COURSE_ITEM:
                id=database.insert("course",null,values);
                mUri=Uri.parse("content://vero.demo.myprovider/course/"+id);
                break;
            default:
                break;
        }
        return mUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String id=null;
        int num=0;
        switch (matcher.match(uri)){
            case USER_DIR:
                num =database.delete("user",selection,selectionArgs);
                break;
            case USER_ITEM:
                id=uri.getPathSegments().get(0);
                num =database.delete("user","id=?",new String[]{id});
                break;
            case COURSE_DIR:
                num =database.delete("course",selection,selectionArgs);
                break;
            case COURSE_ITEM:
                id=uri.getPathSegments().get(0);
                num =database.delete("course","id=?",new String[]{id});
        }
        return num;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int num=0;
        String id=null;
        switch (matcher.match(uri)){
            case USER_DIR:
                num=database.update("user",values,selection,selectionArgs);
                break;
            case USER_ITEM:
                id=uri.getPathSegments().get(0);
                num=database.update("user",values,"_id=?",new String[]{id});
                break;
            case COURSE_DIR:
                num=database.update("course",values,selection,selectionArgs);
                break;
            case COURSE_ITEM:
                id=uri.getPathSegments().get(0);
                num=database.update("course",values,"_id=?",new String[]{id});
                break;
        }
        return num;
    }
    @Override
    public String getType(Uri uri) {
        String type=null;
        String dir_body="vnd.android.cursor.dir/+vnd.";
        String item_body="vnd.android.cursor.item/+vnd.";
        String dir_tial=uri.getPathSegments().get(0);
        String item_tial=uri.getPathSegments().get(0)+uri.getPathSegments().get(1);
        switch (matcher.match(uri)){
            case USER_DIR:
                type=dir_body+dir_tial;
                break;
            case USER_ITEM:
                type=item_body+item_tial;
                break;
            case COURSE_DIR:
                type=dir_body+dir_tial;
                break;
            case COURSE_ITEM:
                type=item_body+item_tial;
                break;
        }
        return type;
    }

}
