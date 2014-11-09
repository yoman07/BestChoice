package com.zdobywacz.ab.classes;

/**
 * Created by yoman on 08.11.14.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BestChoiceStats extends SQLiteOpenHelper {
    private static final String TAG = "BestChoiceStats";
    private static final String DATABASE_NAME = "bestchoice.db";
    private static final int DATABASE_VERSION = 1;


    public BestChoiceStats(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BestChoiceStats(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS abcache (name TEXT, choice INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS abcache");
        onCreate(db);
    }

    public int getCachedChoice(String name) {
        int resp = -1;
        SQLiteDatabase db = getReadableDatabase();
        String[] args = {name};
        Cursor cur = db.rawQuery("SELECT choice FROM abcache WHERE name = ?", args);
        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            resp = cur.getInt(0);
            cur.moveToNext();
        }
        db.close();
        return resp;
    }

    public void setCachedChoice(String name, int choice) {
        SQLiteDatabase db = this.getWritableDatabase();
        Object[] args = {name, choice};
        db.execSQL("INSERT INTO abcache (name, choice) VALUES (?, ?)", args);
        db.close();
    }

}
