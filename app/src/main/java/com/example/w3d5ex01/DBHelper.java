package com.example.w3d5ex01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "thiDatabase.db";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "+ FeedReaderContract.FeedEntries.TABLE_NAME+"("+
                    FeedReaderContract.FeedEntries._ID+" INTEGER PRIMARY KEY,"+
                    FeedReaderContract.FeedEntries.COLUMN_NAME+" TEXT,"+
                    FeedReaderContract.FeedEntries.COLUMN_LAST+" TEXT,"+
                    FeedReaderContract.FeedEntries.COLUMN_CITY+" TEXT,"+
                    FeedReaderContract.FeedEntries.COLUMN_EMAIL+" TEXT,"+
                    FeedReaderContract.FeedEntries.COLUMN_IMAGE+" TEXT)";

    public static final String SQL_DELETE_ENTRIES ="DROP TABLE IF EXIST "+
            FeedReaderContract.FeedEntries.TABLE_NAME;

    public DBHelper(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
