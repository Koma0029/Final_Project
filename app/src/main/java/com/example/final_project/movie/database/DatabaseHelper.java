package com.example.final_project.movie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.final_project.movie.model.ResponseData;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "url_database";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_NAME2 = "Movies";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_ACTOR = "actors";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableQuery = "CREATE TABLE " + TABLE_NAME2 + "("
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_YEAR + " TEXT,"
                + COLUMN_ACTOR + " TEXT,"
                + COLUMN_URL + " TEXT"
                + ")";
        db.execSQL(createTableQuery);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);


    }


    public boolean insertMovies(ResponseData responseData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, responseData.getTitle());
        values.put(COLUMN_YEAR, responseData.getYear());
        values.put(COLUMN_ACTOR, responseData.getActors());
        values.put(COLUMN_URL, responseData.getPoster());
        long result = db.insert(TABLE_NAME2, null, values);
        db.close();
        return result != -1;
    }
    public boolean deleteUrl(String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME2, COLUMN_URL + "=?", new String[]{url});
        db.close();
        return result > 0; // Returns true if deletion was successful
    }

    public List<ResponseData> getAllUrls() {
        List<ResponseData> movieList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2, null);
        if (cursor.moveToFirst()) {
            do {
                System.out.println("cursor $cursor");
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                String year = cursor.getString(cursor.getColumnIndex(COLUMN_YEAR));
                String actor = cursor.getString(cursor.getColumnIndex(COLUMN_ACTOR));
                String url = cursor.getString(cursor.getColumnIndex(COLUMN_URL));
                ResponseData responseData = new ResponseData();
                responseData.setTitle(title);
                responseData.setYear(year);
                responseData.setActors(actor);
                responseData.setPoster(url);
                movieList.add(responseData);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movieList;
    }
}