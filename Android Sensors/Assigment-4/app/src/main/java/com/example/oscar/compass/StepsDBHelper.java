package com.example.oscar.compass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Oscar on 2017-02-18.
 */

public class StepsDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StepsDatabase";
    private static final String TABLE_STEPS_SUMMARY ="StepsSummary";
    private static final String ID = "id";
    private static final String STEPS_COUNT ="stepscount";
    private static final String CREATION_DATE ="creationdate";
    private static final String CREATE_TABLE_STEPS_SUMMARY = "CREATE TABLE "+
            TABLE_STEPS_SUMMARY + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            CREATION_DATE + " TEXT,"+ STEPS_COUNT + " INTEGER"+")";

    public StepsDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STEPS_SUMMARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /**
     * Method that create a new step which increses the number of steps taken
     * @return
     */
    public boolean createStepsEntry() {
        boolean isDateAlreadyPresent = false;
        boolean createSuccessful = false;
        double currentDateStepCounts = 0;

        String selectQuery = "SELECT " + STEPS_COUNT + " FROM " + TABLE_STEPS_SUMMARY;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst()) {
                do {
                    currentDateStepCounts =c.getInt((c.getColumnIndex(STEPS_COUNT)));
                } while (c.moveToNext());
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(STEPS_COUNT,++currentDateStepCounts);
            db.insert(TABLE_STEPS_SUMMARY,null, values);
            createSuccessful = true;
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createSuccessful;
    }

    /**
     * Method that returns the number of steps taken
     * @return
     */
    public double readStepsEntries(){
        double nbrSteps = 0;
        String selectQuery = "SELECT * FROM " +TABLE_STEPS_SUMMARY;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst()) {
                do {
                    nbrSteps = c.getInt(c.getColumnIndex(STEPS_COUNT));
                } while (c.moveToNext());
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nbrSteps;
    }

    /**
     * Method that drops databases table
     */
    public void deleteAllEntries() {
        String selectQuery = "DELETE FROM " +TABLE_STEPS_SUMMARY;
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor c = db.rawQuery(selectQuery, null);
        db.execSQL(selectQuery);
        db.close();
    }
}
