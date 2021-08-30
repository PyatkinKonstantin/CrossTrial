package com.kos.crosstrial.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.TABLE_STITCHES_STRUCTURE);
        db.execSQL(Constants.TABLE_THREADS_STRUCTURE);
        db.execSQL(Constants.TABLE_CURRENT_THREADS_STRUCTURE);
        db.execSQL(Constants.TABLE_FABRIC_STRUCTURE);
        db.execSQL(Constants.TABLE_CUT_FABRIC_STRUCTURE);

        db.execSQL(Constants.TABLE_SET_STRUCTURE);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.KOL_ZAP, "0");
        contentValues.put(Constants.FIRST_START, 0);
        contentValues.put(Constants.PASM, 1);
        db.insert(Constants.TABLE_SET, null, contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DROP_TABLE_STITCHES);
        db.execSQL(Constants.DROP_TABLE_THREADS);
        db.execSQL(Constants.DROP_TABLE_CURRENT_THREADS);
        db.execSQL(Constants.DROP_TABLE_FABRIC);
        db.execSQL(Constants.DROP_TABLE_CUT_FABRIC);
        db.execSQL(Constants.DROP_TABLE_SET);
        onCreate(db);
    }
}
