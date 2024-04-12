package com.example.final_project.pixels.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PexelDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pexel";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_NAME = "url";

    private static final String TABLE_NAME2 = "image_url";
    private static final String COLUMN_URL = "url";

    public PexelDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_URL + " TEXT)";
        db.execSQL(createTableQuery);
        String tableQuery = "CREATE TABLE " + TABLE_NAME2 + " (" +
                COLUMN_URL + " TEXT)";
        db.execSQL(tableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
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
public boolean insertUrl(String url) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COLUMN_URL, url);
    long result = db.insert(TABLE_NAME2, null, values);
    db.close();
    return result != -1; // Returns true if insertion was successful
}

    public boolean deleteUrl(String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME2, COLUMN_URL + "=?", new String[]{url});
        db.close();
        return result > 0; // Returns true if deletion was successful
    }

    public List<String> getAllUrls() {
        List<String> urls = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2, null);
        if (cursor.moveToFirst()) {
            do {
                System.out.println("cursor $cursor");
                String url = cursor.getString(cursor.getColumnIndex(COLUMN_URL));
                urls.add(url);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return urls;
    }



}
