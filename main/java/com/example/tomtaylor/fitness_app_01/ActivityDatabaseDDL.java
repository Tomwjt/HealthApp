package com.example.tomtaylor.fitness_app_01;

import android.provider.BaseColumns;

/**
 * Created by tom.taylor on 09/06/2017.
 */

public final class ActivityDatabaseDDL {

    public ActivityDatabaseDDL(){}

    public static class ActivityTable implements BaseColumns {
        public static final String TABLE_NAME = "ACTIVITY_LOG";
        public static final String COLUMN_PRIMARY_KEY = "ACTIVITY_TABLE_PK";
        public static final String COLUMN_ACTIVITY_NAME = "ACTIVITY_NAME";
        public static final String COLUMN_DAY_OF_ACTIVITY = "DAY_OF_ACTIVITY";
        public static final String COLUMN_AMOUNT_OF_TIME = "AMOUNT_OF_TIME";
    }

    public static final String SQL_CREATE_ACTIVITY_TABLE =
            "CREATE TABLE " + ActivityTable.TABLE_NAME + " (" +
            ActivityTable.COLUMN_PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ActivityTable.COLUMN_ACTIVITY_NAME + " TEXT," +
            ActivityTable.COLUMN_DAY_OF_ACTIVITY + " TEXT," +
            ActivityTable.COLUMN_AMOUNT_OF_TIME + " TEXT)";


}
