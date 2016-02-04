package com.ofirelarat.tripil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_USERS =
            "CREATE TABLE " + DBContract.DBUser.TABLE_NAME + "(" +
                    DBContract.DBUser._ID + " INTEGER PRIMARY KEY," +
                    DBContract.DBUser.COLUMN_NAME_USERNAME + "TEXT NOT NULL" +
                    DBContract.DBUser.COLUMN_NAME_FIRST_NAME + " TEXT," +
                    DBContract.DBUser.COLUMN_NAME_LAST_NAME + " TEXT," +
                    DBContract.DBUser.COLUMN_NAME_PASSWORD + " TEXT NOT NULL," +
                    DBContract.DBUser.COLUMN_NAME_MAIL + " TEXT," +
                    DBContract.DBUser.COLUMN_NAME_PHONE + " TEXT)";

    private static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + DBContract.DBUser.TABLE_NAME;

    private static final String SQL_CREATE_TRIPS =
            "CREATE TABLE " + DBContract.DBTrip.TABLE_NAME + "(" +
                    DBContract.DBTrip._ID + " INTEGER PRIMARY KEY," +
                    DBContract.DBTrip.COLUMN_NAME_USERNAME + "TEXT NOT NULL" +
                    DBContract.DBTrip.COLUMN_NAME_ARRAIVAL + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_RETURN + " TEXT, " +
                    DBContract.DBTrip.COLUMN_NAME_DAYS + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_AREA + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_HOTEL + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_ATTRACTION + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_STARS + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_TRAVEL_GUIDE + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_TRIPS =
            "DROP TABLE IF EXISTS " + DBContract.DBTrip.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TripIL.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_TRIPS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USERS);
        db.execSQL(SQL_DELETE_TRIPS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
