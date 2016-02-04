package com.ofirelarat.tripil;

import android.provider.BaseColumns;

public final class DBContract {
    public DBContract() {}

    public static abstract class DBUser implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME = "last_name";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_MAIL = "mail";
        public static final String COLUMN_NAME_PHONE = "phone";
    }

    public static abstract class DBTrip implements BaseColumns {
        public static final String TABLE_NAME = "trips";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_ARRAIVAL = "arraive_date";
        public static final String COLUMN_NAME_RETURN = "return_date";
        public static final String COLUMN_NAME_DAYS = "days";
        public static final String COLUMN_NAME_AREA = "erea";
        public static final String COLUMN_NAME_HOTEL = "hotels";
        public static final String COLUMN_NAME_ATTRACTION = "attraction";
        public static final String COLUMN_NAME_STARS = "stars";
        public static final String COLUMN_NAME_TRAVEL_GUIDE = "travel_guide";
        public static final String COLUMN_NAME_DESCRITION = "description";
    }
}
