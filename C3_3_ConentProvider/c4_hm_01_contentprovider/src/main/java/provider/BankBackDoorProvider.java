package provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

public class BankBackDoorProvider extends ContentProvider {
    public BankBackDoorProvider() {
    }


    /**
     * 安全保障：保安：UriMatcher
     * 匹配失败就NO_MATCH
     */
    private static UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
    private static final int SUCCESS = 1;

    static {
        matcher.addURI("com.vero.BankBackDoorProvider","accounts",SUCCESS);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i("BankBackDoor","insert");
        int result=matcher.match(uri);
        if (SUCCESS==result){
            Log.i("BankBackDoorProvider","uri---正确了");
        }else {
            throw  new RuntimeException("暗号不对，滚");
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i("BankBackDoor","delete");
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i("BankBackDoor","update");
        return 0;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i("BankBackDoor","query");
        return null;
    }
}
