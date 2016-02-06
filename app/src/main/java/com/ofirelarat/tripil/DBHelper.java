package com.ofirelarat.tripil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    private static final String SQL_CREATE_USERS =
            "CREATE TABLE " + DBContract.DBUser.TABLE_NAME + "(" +
                    DBContract.DBUser.COLUMN_NAME_USERNAME + "TEXT PRIMARY KEY," +
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
                    DBContract.DBTrip.COLUMN_NAME_USERNAME + "TEXT NOT NULL," +
                    DBContract.DBTrip.COLUMN_NAME_ARRAIVAL + " TEXT NOT NULL," +
                    DBContract.DBTrip.COLUMN_NAME_RETURN + " TEXT NOT NULL, " +
                    DBContract.DBTrip.COLUMN_NAME_AREA + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_HOTEL + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_ATTRACTION + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_STARS + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_TRAVEL_GUIDE + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_PICTURES + " TEXT)";

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

    public void insertTrip(Trip trip) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.DBTrip.COLUMN_NAME_USERNAME, trip.getUsername());
        values.put(DBContract.DBTrip.COLUMN_NAME_ARRAIVAL, trip.getArrivalDate());
        values.put(DBContract.DBTrip.COLUMN_NAME_RETURN, trip.getReturnDate());
        values.put(DBContract.DBTrip.COLUMN_NAME_AREA, trip.getArea());
        values.put(DBContract.DBTrip.COLUMN_NAME_HOTEL, ArrayToString(trip.getHotels()));
        values.put(DBContract.DBTrip.COLUMN_NAME_ATTRACTION, ArrayToString(trip.getAttractions()));
        values.put(DBContract.DBTrip.COLUMN_NAME_STARS, trip.getStars());
        values.put(DBContract.DBTrip.COLUMN_NAME_TRAVEL_GUIDE, trip.getTravelGuide());
        values.put(DBContract.DBTrip.COLUMN_NAME_DESCRIPTION, trip.getDescription());
        values.put(DBContract.DBTrip.COLUMN_NAME_PICTURES, ArrayToString(trip.getPictures()));

        db.insert(DBContract.DBTrip.TABLE_NAME, null, values);
        db.close();
    }

    //delete trip from the database
    public void DeleteTrip(String trip_id){
        String selection = DBContract.DBTrip._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(trip_id) };
        db.delete(DBContract.DBTrip.TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    //delete user from database
    public void DeleteUser(String username){
        String selection = DBContract.DBUser.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = { String.valueOf(username) };
        db.delete(DBContract.DBUser.TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    //find trip by id
    public Trip FindTripsByUserId(int id){
        db = this.getReadableDatabase();

        String[] projection = {
                DBContract.DBTrip._ID,
                DBContract.DBTrip.COLUMN_NAME_USERNAME,
                DBContract.DBTrip.COLUMN_NAME_ARRAIVAL,
                DBContract.DBTrip.COLUMN_NAME_RETURN,
                DBContract.DBTrip.COLUMN_NAME_AREA,
                DBContract.DBTrip.COLUMN_NAME_HOTEL,
                DBContract.DBTrip.COLUMN_NAME_ATTRACTION,
                DBContract.DBTrip.COLUMN_NAME_STARS,
                DBContract.DBTrip.COLUMN_NAME_TRAVEL_GUIDE,
                DBContract.DBTrip.COLUMN_NAME_DESCRIPTION,
                DBContract.DBTrip.COLUMN_NAME_PICTURES
        };

        String selection = DBContract.DBTrip._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor c = db.query(
                DBContract.DBTrip.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        db.close();
        c.moveToFirst();

        return new Trip(
                Integer.getInteger(String.valueOf(c.getLong(0))),
                c.getString(1),
                c.getString(2),
                c.getString(3),
                c.getString(4),
                c.getString(5),
                c.getString(6),
                c.getString(7),
                c.getString(8),
                c.getString(9)
            );
    }

    //find trip by user name
    public List<Trip> FindTripsByUser(String username){
        db = this.getReadableDatabase();

        String[] projection = {
                DBContract.DBTrip._ID,
                DBContract.DBTrip.COLUMN_NAME_USERNAME,
                DBContract.DBTrip.COLUMN_NAME_ARRAIVAL,
                DBContract.DBTrip.COLUMN_NAME_RETURN,
                DBContract.DBTrip.COLUMN_NAME_AREA,
                DBContract.DBTrip.COLUMN_NAME_HOTEL,
                DBContract.DBTrip.COLUMN_NAME_ATTRACTION,
                DBContract.DBTrip.COLUMN_NAME_STARS,
                DBContract.DBTrip.COLUMN_NAME_TRAVEL_GUIDE,
                DBContract.DBTrip.COLUMN_NAME_DESCRIPTION,
                DBContract.DBTrip.COLUMN_NAME_PICTURES
        };

        String sortOrder = DBContract.DBTrip._ID + " DESC";
        String selection = DBContract.DBTrip.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = { String.valueOf(username) };

        Cursor c = db.query(
                DBContract.DBTrip.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        db.close();
        c.moveToFirst();
        List<Trip> trips = new ArrayList<>();

        do {
            trips.add(new Trip(
                    Integer.getInteger(String.valueOf(c.getLong(0))),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(7),
                    c.getString(8),
                    c.getString(9)
            ));
        } while(c.moveToNext());

        c.close();

        return trips;
    }

    //find user
    public Cursor findUser(String userName,String password){
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DBContract.DBUser.TABLE_NAME + " WHERE " +
                DBContract.DBUser.COLUMN_NAME_USERNAME + "=" + userName + "and" + DBContract.DBUser.COLUMN_NAME_PASSWORD + "=" + password + "", null);
        db.close();

        return res;
    }

    // check if user is already exist
    public Cursor isExistUser(String userName){
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DBContract.DBUser.TABLE_NAME + " WHERE " + DBContract.DBUser.COLUMN_NAME_USERNAME + "=" + userName, null);
        db.close();

        return res;
    }

    //get a user pass by  name
    public String getPassUser(String username){
        db = getReadableDatabase();
        String query = "SELECT " + DBContract.DBUser.COLUMN_NAME_USERNAME + "," +
                DBContract.DBUser.COLUMN_NAME_PASSWORD + " FROM " + DBContract.DBUser.TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        String pass = "not exist";
        String name;

        if(cursor.moveToFirst())
        {
            do{
                name = cursor.getString(0);

                if(name.equals(username)){
                    pass = cursor.getString(1);

                    break;
                }
            }  while(cursor.moveToNext());

        }

        return pass;
    }

    public void AddUser(User user) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.DBUser.COLUMN_NAME_USERNAME, user.username);
        values.put(DBContract.DBUser.COLUMN_NAME_PASSWORD, user.password);
        values.put(DBContract.DBUser.COLUMN_NAME_FIRST_NAME, user.firstName);
        values.put(DBContract.DBUser.COLUMN_NAME_LAST_NAME, user.lastName);
        values.put(DBContract.DBUser.COLUMN_NAME_MAIL, user.mail);
        values.put(DBContract.DBUser.COLUMN_NAME_PHONE, user.phone);

        long newRowId = db.insert(DBContract.DBUser.TABLE_NAME, null, values);
        db.close();
    }

    //update trip
    public void updateTrip(Trip trip){
        ContentValues values = new ContentValues();
        values.put(DBContract.DBTrip.COLUMN_NAME_USERNAME, trip.getUsername());
        values.put(DBContract.DBTrip.COLUMN_NAME_ARRAIVAL, trip.getArrivalDate());
        values.put(DBContract.DBTrip.COLUMN_NAME_RETURN, trip.getReturnDate());
        values.put(DBContract.DBTrip.COLUMN_NAME_AREA, trip.getArea());
        values.put(DBContract.DBTrip.COLUMN_NAME_HOTEL, ArrayToString(trip.getHotels()));
        values.put(DBContract.DBTrip.COLUMN_NAME_ATTRACTION, ArrayToString(trip.getAttractions()));
        values.put(DBContract.DBTrip.COLUMN_NAME_STARS, trip.getStars());
        values.put(DBContract.DBTrip.COLUMN_NAME_TRAVEL_GUIDE, trip.getTravelGuide());
        values.put(DBContract.DBTrip.COLUMN_NAME_DESCRIPTION, trip.getDescription());
        values.put(DBContract.DBTrip.COLUMN_NAME_PICTURES, ArrayToString(trip.getPictures()));

        db = getWritableDatabase();
        db.update(DBContract.DBTrip.TABLE_NAME, values, DBContract.DBTrip._ID + "=" + trip.getId(), null);
        db.close();
    }

    private String ArrayToString(String[] arr){
        String temp = Arrays.toString(arr);
        return temp.substring(1, temp.length()-1);
    }
}
