package com.ofirelarat.tripil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.net.ContentHandler;
import java.sql.SQLException;

public final class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TripIL.db";
    private static final String TABLE_TRIPS = "Trips";
    private static final String COLMN_ID = "id_trip";
    private static final String COLMN_USERNAME = "username_trip";
    private static final String COLMN_ARRAIVAL = "arraive_date";
    private static final String COLMN_RETURN = "return_date";
    private static final String COLMN_DAYS = "days";
    private static final String COLMN_AREA = "erea";
    private static final String COLMN_HOTEL = "hotels";
    private static final String COLMN_ATTRACTION = "attraction";
    private static final String COLMN_STARS = "stars";
    private static final String COLMN_TRAVELGUIDE = "travel_guide";
    private static final String COLMN_DISCRITION = "discription";
    private static final String TABLE_USERS = "Users";
    private static final String COLMN_FIRSTNAME = "first_name";
    private static final String COLMN_LASTNAME = "last_name";
    private static final String COLMN_PASSWORD = "password";
    private static final String COLMN_MAIL = "mail";
    private static final String COLMN_PHONE = "phone";

    SQLiteDatabase db;
    String dbFileName = DATABASE_NAME + ".db";
    private static String DB_PATH = "/data/data/com.ofirelarat.tripil/database/";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);
        // String myPath=context.getFilesDir().getAbsolutePath().replace("files","databases")+ File.separator+DATABASE_NAME;
        try {
            File databaseFile = context.getDatabasePath(dbFileName);
            db = SQLiteDatabase.openOrCreateDatabase(databaseFile,null);
        }catch (Exception e){
            String databasePath = context.getFilesDir().getPath() + "/" + dbFileName;
            File databaseFile = new File(databasePath);
            db = SQLiteDatabase.openOrCreateDatabase(databaseFile, null);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryUser = "CREATE TABLE " + TABLE_USERS + "(" +
                COLMN_USERNAME + " TEXT PRIMARY KEY NOT NULL , " +
                COLMN_PASSWORD + " TEXT NOT NULL , " +
                COLMN_FIRSTNAME + " TEXT , " +
                COLMN_PASSWORD + " TEXT , " +
                COLMN_MAIL + " TEXT , " +
                COLMN_PHONE + " TEXT" + ");";
        db.execSQL(queryUser);

        String queryTrip = "CREATE TABLE" + TABLE_TRIPS + "(" +
                COLMN_ID + "INTEGER PRIMARY KEY NOT NULL AUTO_INCREAMENT," +
                COLMN_USERNAME + "TEXT NOT NULL," +
                COLMN_ARRAIVAL + "TEXT," +
                COLMN_RETURN + "TEXT," +
                COLMN_DAYS + "INTEGER," +
                COLMN_AREA + "TEXT," +
                COLMN_HOTEL + "TEXT," +
                COLMN_ATTRACTION + "TEXT," +
                COLMN_STARS + "INTEGER," +
                COLMN_TRAVELGUIDE + "TEXT," +
                COLMN_DISCRITION + "TEXT" + ");";
        db.execSQL(queryTrip);

        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //  db.execSQL("DROP TABLE IF EXIST" + TABLE_TRIPS);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_USERS);
        onCreate(db);
    }

    //Add a trip  to the database
    public void insertTrip(Trip trip) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "SELECT * FROM " + TABLE_TRIPS;
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();

        values.put(COLMN_USERNAME, count);
        values.put(COLMN_ARRAIVAL, trip.getArrivalDate());
        values.put(COLMN_RETURN, trip.getReturnDate());
        values.put(COLMN_DAYS, trip.getArea());
        values.put(COLMN_AREA, trip.getDays());
        values.put(COLMN_HOTEL, trip.getHotels());
        values.put(COLMN_ATTRACTION, trip.getAttractions());
        values.put(COLMN_STARS, trip.getStars());
        values.put(COLMN_TRAVELGUIDE, trip.getTravelGuide());
        values.put(COLMN_DISCRITION, trip.getDescription());

        db.insert(TABLE_TRIPS, null, values);
        db.close();
    }

    //add user to the  database
    public void insertUser(User user){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLMN_USERNAME,user.getUsername());
        values.put(COLMN_PASSWORD,user.getPassword());
        values.put(COLMN_FIRSTNAME,user.getFirstName());
        values.put(COLMN_LASTNAME,user.getLastName());
        values.put(COLMN_MAIL,user.getMail());
        values.put(COLMN_PHONE, user.getPhone());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    //delete trip from the database
    public void deleteTrip(String trip_id){
        db = this.getReadableDatabase();
        db.execSQL("DELETE FROM" + TABLE_TRIPS + "WHERE" + COLMN_ID + "=\"" + trip_id + "\";");
    }

    //delete user from database
    public void deleteUser(String userName){
        db = this.getReadableDatabase();
        db.execSQL("DELETE FROM"+TABLE_USERS+"WHERE"+COLMN_USERNAME+"=\""+userName+"\";");
    }

    //find trip by user name
    public Cursor findTrip(String userName){
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TRIPS + " WHERE " + COLMN_ID + "=" + userName + "", null);
        return res;
    }

    //find user
    public Cursor findUser(String userName,String password){
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLMN_ID + "=" + userName +
                "and" + COLMN_PASSWORD + "=" + password + "", null);
        return res;
    }

    // check if user is already exist
    public Cursor isExistUser(String userName){
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLMN_ID + "=" + userName + "", null);
        return res;
    }

    //get a user pass by  name
    public String getPassUser(String uName){
        db = this.getReadableDatabase();
        String query = "SELECT " + COLMN_USERNAME + "," + COLMN_PASSWORD + " FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query,null);
        String pass = "not exist";
        String name;

        if(cursor.moveToFirst())
         {
            do{
                name = cursor.getString(0);

                if(name.equals(uName)){
                    pass = cursor.getString(1);

                    break;
                }
            }  while(cursor.moveToNext());

         }

        return pass;
    }

    //update trip
    public void updateTrip(Trip trip){
        ContentValues values = new ContentValues();
        values.put(COLMN_USERNAME, trip.getUsername());
        values.put(COLMN_ARRAIVAL, trip.getArrivalDate());
        values.put(COLMN_RETURN, trip.getReturnDate());
        values.put(COLMN_DAYS, trip.getArea());
        values.put(COLMN_AREA, trip.getDays());
        values.put(COLMN_HOTEL, trip.getHotels());
        values.put(COLMN_ATTRACTION, trip.getAttractions());
        values.put(COLMN_STARS, trip.getStars());
        values.put(COLMN_TRAVELGUIDE, trip.getTravelGuide());
        values.put(COLMN_DISCRITION, trip.getDescription());

        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_TRIPS, values, COLMN_ID + "=" + trip.getId(), null);
        db.close();
    }
}
