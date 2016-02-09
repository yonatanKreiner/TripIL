package com.ofirelarat.tripil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    private static final String SQL_CREATE_USERS =
            "CREATE TABLE " + DBContract.DBUser.TABLE_NAME + "(" +
                    DBContract.DBUser.COLUMN_NAME_USERNAME + " TEXT PRIMARY KEY," +
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
                    DBContract.DBTrip.COLUMN_NAME_USERNAME + " TEXT NOT NULL," +
                    DBContract.DBTrip.COLUMN_NAME_ARRAIVAL + " TEXT NOT NULL," +
                    DBContract.DBTrip.COLUMN_NAME_RETURN + " TEXT NOT NULL, " +
                    DBContract.DBTrip.COLUMN_NAME_AREA + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_HOTEL + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_ATTRACTION + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_STARS + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_RATES_COUNT + " INTEGER," +
                    DBContract.DBTrip.COLUMN_NAME_TRAVEL_GUIDE + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    DBContract.DBTrip.COLUMN_NAME_PICTURES + " TEXT, FOREIGN KEY(" +
                    DBContract.DBTrip.COLUMN_NAME_USERNAME + ") REFERENCES " +
                    DBContract.DBUser.TABLE_NAME + "(" + DBContract.DBUser.COLUMN_NAME_USERNAME + "))";

    private static final String SQL_DELETE_TRIPS =
            "DROP TABLE IF EXISTS " + DBContract.DBTrip.TABLE_NAME;

    public static final int DATABASE_VERSION = 20;
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

    public boolean AddTrip(Trip trip) {
        db = getWritableDatabase();

        if(FindTripsById(trip.id) == null) {
            ContentValues values = new ContentValues();
            values.put(DBContract.DBTrip.COLUMN_NAME_USERNAME, trip.getUsername());
            values.put(DBContract.DBTrip.COLUMN_NAME_ARRAIVAL, trip.getArrivalDate());
            values.put(DBContract.DBTrip.COLUMN_NAME_RETURN, trip.getReturnDate());
            values.put(DBContract.DBTrip.COLUMN_NAME_AREA, trip.getArea());
            values.put(DBContract.DBTrip.COLUMN_NAME_HOTEL, Common.ArrayToString(trip.getHotels()));
            values.put(DBContract.DBTrip.COLUMN_NAME_ATTRACTION, Common.ArrayToString(trip.getAttractions()));
            values.put(DBContract.DBTrip.COLUMN_NAME_STARS, trip.getStars());
            values.put(DBContract.DBTrip.COLUMN_NAME_RATES_COUNT, trip.getNumOfRates());
            values.put(DBContract.DBTrip.COLUMN_NAME_TRAVEL_GUIDE, trip.getTravelGuide());
            values.put(DBContract.DBTrip.COLUMN_NAME_DESCRIPTION, trip.getDescription());
            values.put(DBContract.DBTrip.COLUMN_NAME_PICTURES, Common.ArrayToString(trip.getPictures()));

            db.insert(DBContract.DBTrip.TABLE_NAME, null, values);
            db.close();

            return true;
        }

        db.close();

        return false;
    }

    public Trip[] GetAll(){
        db = getReadableDatabase();

        String[] projection = {
                DBContract.DBTrip._ID,
                DBContract.DBTrip.COLUMN_NAME_USERNAME,
                DBContract.DBTrip.COLUMN_NAME_ARRAIVAL,
                DBContract.DBTrip.COLUMN_NAME_RETURN,
                DBContract.DBTrip.COLUMN_NAME_AREA,
                DBContract.DBTrip.COLUMN_NAME_HOTEL,
                DBContract.DBTrip.COLUMN_NAME_ATTRACTION,
                DBContract.DBTrip.COLUMN_NAME_STARS,
                DBContract.DBTrip.COLUMN_NAME_RATES_COUNT,
                DBContract.DBTrip.COLUMN_NAME_TRAVEL_GUIDE,
                DBContract.DBTrip.COLUMN_NAME_DESCRIPTION,
                DBContract.DBTrip.COLUMN_NAME_PICTURES
        };

        Cursor c = db.query(
                DBContract.DBTrip.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        db.close();

        try{
            if (c != null && c.moveToNext()){
                List<Trip> trips = new ArrayList<>();

                do {
                    Trip t = new Trip(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getString(4),
                            c.getString(5),
                            c.getString(6),
                            c.getString(9),
                            c.getString(10),
                            c.getString(11)
                    );

                    t.setStars(Integer.getInteger(c.getString(7)));
                    t.setNumOfRates(Integer.getInteger(c.getString(8)));
                    trips.add(t);
                } while(c.moveToNext());


                return (Trip[])trips.toArray();
            }

            return  null;
        } catch(Exception e){
            return null;
        } finally{
            if (c != null)
                c.close();
        }
    }

    //find trip by id
    public Trip FindTripsById(int id){
        db = getReadableDatabase();

        String[] projection = {
                DBContract.DBTrip._ID,
                DBContract.DBTrip.COLUMN_NAME_USERNAME,
                DBContract.DBTrip.COLUMN_NAME_ARRAIVAL,
                DBContract.DBTrip.COLUMN_NAME_RETURN,
                DBContract.DBTrip.COLUMN_NAME_AREA,
                DBContract.DBTrip.COLUMN_NAME_HOTEL,
                DBContract.DBTrip.COLUMN_NAME_ATTRACTION,
                DBContract.DBTrip.COLUMN_NAME_STARS,
                DBContract.DBTrip.COLUMN_NAME_RATES_COUNT,
                DBContract.DBTrip.COLUMN_NAME_TRAVEL_GUIDE,
                DBContract.DBTrip.COLUMN_NAME_DESCRIPTION,
                DBContract.DBTrip.COLUMN_NAME_PICTURES
        };

        String selection = DBContract.DBTrip._ID + "=?";
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

        try{
            if (c != null && c.moveToNext()){
                Trip t = new Trip(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(9),
                        c.getString(10),
                        c.getString(11)
                );

                t.setStars(Integer.getInteger(c.getString(7)));
                t.setNumOfRates(Integer.getInteger(c.getString(8)));

                return t;
            }
        } catch(Exception e){
            return null;
        } finally {
            if (c != null)
                c.close();
        }

        return null;
    }

    //find trip by user name
    public Trip[] FindTripsByUser(String username){
        db = getReadableDatabase();

        String[] projection = {
                DBContract.DBTrip._ID,
                DBContract.DBTrip.COLUMN_NAME_USERNAME,
                DBContract.DBTrip.COLUMN_NAME_ARRAIVAL,
                DBContract.DBTrip.COLUMN_NAME_RETURN,
                DBContract.DBTrip.COLUMN_NAME_AREA,
                DBContract.DBTrip.COLUMN_NAME_HOTEL,
                DBContract.DBTrip.COLUMN_NAME_ATTRACTION,
                DBContract.DBTrip.COLUMN_NAME_STARS,
                DBContract.DBTrip.COLUMN_NAME_RATES_COUNT,
                DBContract.DBTrip.COLUMN_NAME_TRAVEL_GUIDE,
                DBContract.DBTrip.COLUMN_NAME_DESCRIPTION,
                DBContract.DBTrip.COLUMN_NAME_PICTURES
        };

        String sortOrder = DBContract.DBTrip._ID + " DESC";
        String selection = DBContract.DBTrip.COLUMN_NAME_USERNAME + "=?";
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
            Trip t = new Trip(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(9),
                    c.getString(10),
                    c.getString(11)
            );

            t.setStars(Integer.getInteger(c.getString(7)));
            t.setNumOfRates(Integer.getInteger(c.getString(8)));
            trips.add(t);
        } while(c.moveToNext());

        c.close();

        return (Trip[]) trips.toArray();
    }

    //update trip
    public void UpdateTrip(Trip trip){
        ContentValues values = new ContentValues();
        values.put(DBContract.DBTrip.COLUMN_NAME_USERNAME, trip.getUsername());
        values.put(DBContract.DBTrip.COLUMN_NAME_ARRAIVAL, trip.getArrivalDate());
        values.put(DBContract.DBTrip.COLUMN_NAME_RETURN, trip.getReturnDate());
        values.put(DBContract.DBTrip.COLUMN_NAME_AREA, trip.getArea());
        values.put(DBContract.DBTrip.COLUMN_NAME_HOTEL, Common.ArrayToString(trip.getHotels()));
        values.put(DBContract.DBTrip.COLUMN_NAME_ATTRACTION, Common.ArrayToString(trip.getAttractions()));
        values.put(DBContract.DBTrip.COLUMN_NAME_STARS, trip.getStars());
        values.put(DBContract.DBTrip.COLUMN_NAME_RATES_COUNT, trip.getNumOfRates());
        values.put(DBContract.DBTrip.COLUMN_NAME_TRAVEL_GUIDE, trip.getTravelGuide());
        values.put(DBContract.DBTrip.COLUMN_NAME_DESCRIPTION, trip.getDescription());
        values.put(DBContract.DBTrip.COLUMN_NAME_PICTURES, Common.ArrayToString(trip.getPictures()));

        db = getWritableDatabase();
        db.update(DBContract.DBTrip.TABLE_NAME, values, DBContract.DBTrip._ID + "=" + trip.getId(), null);
        db.close();
    }

    //delete trip from the database
    public void DeleteTrip(String trip_id){
        String selection = DBContract.DBTrip._ID + " = ?";
        String[] selectionArgs = { String.valueOf(trip_id) };
        db.delete(DBContract.DBTrip.TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    public boolean AddUser(User user) {
        db = getWritableDatabase();

        if(FindUser(user.username, user.password) == null){
            ContentValues values = new ContentValues();
            values.put(DBContract.DBUser.COLUMN_NAME_USERNAME, user.username);
            values.put(DBContract.DBUser.COLUMN_NAME_PASSWORD, user.password);
            values.put(DBContract.DBUser.COLUMN_NAME_FIRST_NAME, user.firstName);
            values.put(DBContract.DBUser.COLUMN_NAME_LAST_NAME, user.lastName);
            values.put(DBContract.DBUser.COLUMN_NAME_MAIL, user.mail);
            values.put(DBContract.DBUser.COLUMN_NAME_PHONE, user.phone);

            long newRowId = db.insert(DBContract.DBUser.TABLE_NAME, null, values);
            db.close();

            return true;
        }

        db.close();

        return false;
    }

    //find user
    public User FindUser(String username, String password){
        db = getReadableDatabase();

        String[] projection = {
                DBContract.DBUser.COLUMN_NAME_USERNAME,
                DBContract.DBUser.COLUMN_NAME_FIRST_NAME,
                DBContract.DBUser.COLUMN_NAME_LAST_NAME,
                DBContract.DBUser.COLUMN_NAME_PASSWORD,
                DBContract.DBUser.COLUMN_NAME_MAIL,
                DBContract.DBUser.COLUMN_NAME_PHONE
        };

        String selection = DBContract.DBUser.COLUMN_NAME_USERNAME + "=? AND " + DBContract.DBUser.COLUMN_NAME_PASSWORD + "=?";
        String[] selectionArgs = { username, password };

        Cursor c = db.query(
                DBContract.DBUser.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if(c.getCount() > 0) {
            c.moveToFirst();

            return new User(
                    c.getString(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5)
            );
        }

        return null;
    }

    //update trip
    public void UpdateUser(User user){
        ContentValues values = new ContentValues();

        values.put(DBContract.DBUser.COLUMN_NAME_FIRST_NAME, user.getUsername());
        values.put(DBContract.DBUser.COLUMN_NAME_LAST_NAME, user.getLastName());
        values.put(DBContract.DBUser.COLUMN_NAME_PASSWORD, user.getPassword());
        values.put(DBContract.DBUser.COLUMN_NAME_MAIL, user.getMail());
        values.put(DBContract.DBUser.COLUMN_NAME_PHONE, user.getPhone());

        db = getWritableDatabase();
        db.update(DBContract.DBUser.TABLE_NAME, values, DBContract.DBUser.COLUMN_NAME_USERNAME + "=" + user.getUsername(), null);
        db.close();
    }

    //delete user from database
    public void DeleteUser(String username){
        String selection = DBContract.DBUser.COLUMN_NAME_USERNAME + "=?";
        String[] selectionArgs = { String.valueOf(username) };
        db.delete(DBContract.DBUser.TABLE_NAME, selection, selectionArgs);
        db.close();
    }
}
