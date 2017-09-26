package com.example.tomtaylor.fitness_app_01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tom.taylor on 19/05/2017.
 */

public class ActivityDataStore extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME  = "ActivityDatabase.db";

    private ActivityDatabaseDDL activityDatabaseDDL = new ActivityDatabaseDDL();

    public ActivityDataStore(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(activityDatabaseDDL.SQL_CREATE_ACTIVITY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ activityDatabaseDDL.SQL_CREATE_ACTIVITY_TABLE);
        db.execSQL(activityDatabaseDDL.SQL_CREATE_ACTIVITY_TABLE);
    }
    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

}
