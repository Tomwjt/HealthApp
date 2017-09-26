package com.example.tomtaylor.fitness_app_01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom.taylor on 25/09/2017.
 */

public class ActivityOperations {

    public static final String LOGTAG = "ACTIVITY_OPS";

    SQLiteOpenHelper dbHandler;
    SQLiteDatabase database;

    private static final String[]  allColumns = {
            ActivityDatabaseDDL.ActivityTable.COLUMN_PRIMARY_KEY,
            ActivityDatabaseDDL.ActivityTable.COLUMN_ACTIVITY_NAME,
            ActivityDatabaseDDL.ActivityTable.COLUMN_AMOUNT_OF_TIME,
            ActivityDatabaseDDL.ActivityTable.COLUMN_DAY_OF_ACTIVITY
    };

    public ActivityOperations(Context context) {
        dbHandler = new ActivityDataStore(context);
    }

    public void open(){
        Log.i(LOGTAG, "Database Opened");
        database = dbHandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database Closed");
        database.close();
    }

    public FitnessActivity addActivity(FitnessActivity activity){

        Log.d("AddActivity",
            activity.getName() +"\n"+
            activity.getAmountOfTime() +"\n"+
            activity.getDay()+"\n");

        ContentValues values = new ContentValues();
        values.put(ActivityDatabaseDDL.ActivityTable.COLUMN_ACTIVITY_NAME, activity.getName());
        values.put(ActivityDatabaseDDL.ActivityTable.COLUMN_AMOUNT_OF_TIME, activity.getAmountOfTime());
        values.put(ActivityDatabaseDDL.ActivityTable.COLUMN_DAY_OF_ACTIVITY, activity.getDay());
        long insertId = database.insert(ActivityDatabaseDDL.ActivityTable.TABLE_NAME, null, values);
        activity.setActivityId(insertId);
        return activity;
    }

    public FitnessActivity getActivity(String activityName) {
        Cursor cursor = database.query(
                ActivityDatabaseDDL.ActivityTable.TABLE_NAME,
                allColumns,
                ActivityDatabaseDDL.ActivityTable.COLUMN_ACTIVITY_NAME + "=?",
                new String[]{String.valueOf(activityName)},
                null,
                null,
                null,
                null);
        if(cursor != null)
            cursor.moveToFirst();

        FitnessActivity activity = new FitnessActivity(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );

        return activity;
    }

    public List<FitnessActivity> getAllActivities(){
        Cursor cursor = database.query(ActivityDatabaseDDL.ActivityTable.TABLE_NAME,
                allColumns, null, null, null, null, null);
        List<FitnessActivity> activities = new ArrayList<>();
        if(cursor.getCount() > 0) {
            while(cursor.moveToFirst()) {
                FitnessActivity activity = new FitnessActivity();
                activity.setActivityId(cursor.getLong(cursor.getColumnIndex(ActivityDatabaseDDL.ActivityTable.COLUMN_PRIMARY_KEY)));
                activity.setName(cursor.getString(cursor.getColumnIndex(ActivityDatabaseDDL.ActivityTable.COLUMN_ACTIVITY_NAME)));
                activity.setAmountOfTime(cursor.getString(cursor.getColumnIndex(ActivityDatabaseDDL.ActivityTable.COLUMN_AMOUNT_OF_TIME)));
                activity.setDay(cursor.getString(cursor.getColumnIndex(ActivityDatabaseDDL.ActivityTable.COLUMN_DAY_OF_ACTIVITY)));
                activities.add(activity);
            }
        }

        return activities;
    }

    public int updateActivity(FitnessActivity activity) {
        ContentValues values = new ContentValues();
        values.put(ActivityDatabaseDDL.ActivityTable.COLUMN_PRIMARY_KEY, activity.getActivityId());
        values.put(ActivityDatabaseDDL.ActivityTable.COLUMN_ACTIVITY_NAME, activity.getName());
        values.put(ActivityDatabaseDDL.ActivityTable.COLUMN_AMOUNT_OF_TIME, activity.getAmountOfTime());
        values.put(ActivityDatabaseDDL.ActivityTable.COLUMN_DAY_OF_ACTIVITY, activity.getDay());

        return database.update(ActivityDatabaseDDL.ActivityTable.TABLE_NAME, values,
                ActivityDatabaseDDL.ActivityTable.COLUMN_PRIMARY_KEY + "=?", new String[] {String.valueOf(activity.getActivityId())});
    }
}
