package com.mobiledev.pubgtracker.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by stphn on 3/18/2018.
 */

public class DBHelper  extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "playerSearch.db";
    private static int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SAVED_REPOS_TABLE =
                "CREATE TABLE " + SearchContract.SavedRepos.TABLE_NAME + "(" +
                        SearchContract.SavedRepos._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        SearchContract.SavedRepos.COLUMN_FULL_NAME + " TEXT NOT NULL " + ");";
        db.execSQL(SQL_CREATE_SAVED_REPOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SearchContract.SavedRepos.TABLE_NAME + ";");
        onCreate(db);
    }
}
