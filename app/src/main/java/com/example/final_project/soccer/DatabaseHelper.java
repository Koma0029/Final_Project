package com.example.final_project.soccer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "soccer_database";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_NAME1 = "match_details";
    private static final String COLUMN_TITLE1 = "title";
    private static final String COLUMN_DATE = "date";
    private static final String TEAM_NAME1 = "teamName1";
    private static final String TEAM_NAME2 = "teamName2";
    private static final String TEAM_URL1 = "teamUrl1";
    private static final String TEAM_URL2 = "teamUrl2";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String matchDetail =  "CREATE TABLE " + TABLE_NAME1 + "("
                + COLUMN_TITLE1 + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + TEAM_NAME1 + " TEXT,"
                + TEAM_NAME2 + " TEXT,"
                + TEAM_URL1 + " TEXT,"
                + TEAM_URL2 + " TEXT"
                + ")";
        db.execSQL(matchDetail);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);


    }

    public boolean insertMatch(SoccerModel soccerModel) {
        /*
   Inserts a new soccer match into the database.
   Returns true if the insertion was successful, otherwise false.
*/
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE1, soccerModel.getTitle());
        values.put(COLUMN_DATE, soccerModel.getDate());
        values.put(TEAM_NAME1, soccerModel.getSide1().getName());
        values.put(TEAM_NAME2, soccerModel.getSide2().getName());
        values.put(TEAM_URL1, soccerModel.getSide1().getUrl());
        values.put(TEAM_URL2, soccerModel.getSide2().getUrl());
        long result = db.insert(TABLE_NAME1, null, values);
        db.close();
        return result != -1;
    }
    public boolean deleteMatch(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME1, COLUMN_TITLE1 + "=?", new String[]{title});
        db.close();
        return result > 0; // Returns true if deletion was successful
    }

    public List<SoccerModel> getAllMatches(){
        /*
   Retrieves all soccer matches from the database.
   Returns a list of SoccerModel objects representing the matches.
*/
        List<SoccerModel> matchList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME1, null);
        if (cursor.moveToFirst()) {
            do {
                String teamUrl1 = cursor.getString(cursor.getColumnIndex(TEAM_URL1));
                String teamUrl2 = cursor.getString(cursor.getColumnIndex(TEAM_URL2));
                String teamName1 = cursor.getString(cursor.getColumnIndex(TEAM_NAME1));
                String teamName2 = cursor.getString(cursor.getColumnIndex(TEAM_NAME2));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE1));
                SoccerModel soccerModel = new SoccerModel();
                soccerModel.setTitle(title);
                soccerModel.setDate(date);
                soccerModel.setSide1(new SoccerModel.Side());
                soccerModel.setSide2(new SoccerModel.Side());
                soccerModel.getSide1().setName(teamName1);
                soccerModel.getSide1().setUrl(teamUrl1);

                soccerModel.getSide2().setName(teamName2);
                soccerModel.getSide2().setUrl(teamUrl2);
                matchList.add(soccerModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return matchList;
    }
}