package com.example.final_project.ticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTicketMaster extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "event_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME1 = "tickets";
    private static final String COLUMN_DATE = "date";

    private static final String COLUMN_MIN = "min";
    private static final String COLUMN_MAX = "max";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_CURRENCY = "currency";
    private static final String COLUMN_TYPE = "type";



    public DatabaseTicketMaster(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("table", "on create sql");
        try {

            String createTable1 = "CREATE TABLE " + TABLE_NAME1 +"("
                    + COLUMN_URL + " TEXT, " +
                    COLUMN_MIN + " DOUBLE, " +
                    COLUMN_MAX + " DOUBLE, " +
                    COLUMN_CURRENCY + " TEXT, " +
                    COLUMN_TYPE + " TEXT)";
            db.execSQL(createTable1);
        }catch (Exception e){
            Log.d("table", "onCreate:"+e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);


    }

//    public boolean insertUrl(String url) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_URL, url);
//        db.insert(TABLE_NAME, null, values);
//        db.close();
//        return true;
//    }
    public boolean inserEvent(String currency,String type, Double min, Double max, String url) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
        values.put(COLUMN_URL, url);
        values.put(COLUMN_MAX,max);
        values.put(COLUMN_MIN,min);
        values.put(COLUMN_CURRENCY,currency);
        values.put(COLUMN_TYPE,type);

//    values.put(COLUMN_Date,type);

    long result = db.insert(TABLE_NAME1, null, values);
    db.close();
   // getAllEvents();
    return result != -1; // Returns true if insertion was successful

    }

    public boolean deletEvent(String url) {
        /* Delete an event from the database based on its URL.
         * - Get a writable database instance.
         * - Perform the delete operation using the URL as the selection criteria.
         * - Close the database.
         * - Return true if deletion was successful, otherwise false.
         */
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME1, COLUMN_URL + "=?", new String[]{url});
        db.close();
        return result > 0; // Returns true if deletion was successful
    }

    public List<Model.EventDetails> getAllEvents() {
        /* Retrieve all events from the database.
         * - Create an empty list to store event details.
         * - Get a readable database instance.
         * - Query the database to select all rows from the specified table.
         * - Iterate over the cursor and extract event details from each row.
         * - Create a new Model.EventDetails object with the retrieved data.
         * - Add the event details object to the list.
         * - Close the cursor and the database.
         * - Return the list of event details.
         */
        List<Model.EventDetails> eventList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME1, null);
        if (cursor.moveToFirst()) {
            do {

//                String date= cursor.getString(cursor.getColumnIndex(COLUMN_Date));
                String currency = cursor.getString(cursor.getColumnIndex(COLUMN_CURRENCY));
                String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
                double min = cursor.getDouble(cursor.getColumnIndex(COLUMN_MIN));
                double max = cursor.getDouble(cursor.getColumnIndex(COLUMN_MAX));
                String url = cursor.getString(cursor.getColumnIndex(COLUMN_URL));


                // Retrieve other columns as needed
                Model.EventDetails eventDetails = new Model.EventDetails(currency, type, min, max, url);
                Log.e("TAG"," eventDetails "+eventDetails.toString());
                eventList.add(eventDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return eventList;
    }
}
