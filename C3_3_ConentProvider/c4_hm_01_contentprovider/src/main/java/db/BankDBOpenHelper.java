package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vero on 2016/5/27.
 */
public class BankDBOpenHelper extends SQLiteOpenHelper{

    public BankDBOpenHelper(Context context) {
        super(context, "bank.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE accounts(" +
                        "_id integer primary key autoincrement," +
                        "name varchar(30),"+
                        "money float"+
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
