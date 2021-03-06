package com.example.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pets.data.PetContract.PetEntry;

import androidx.annotation.Nullable;

public class PetDbHelper extends SQLiteOpenHelper {
    private static  final String DATABASE_NAME="shelter.db";
    private static  final  int DATABASE_VERSION=1;
    String LOG_TAG=PetDbHelper.class.getSimpleName();

    public PetDbHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE="CREATE TABLE "+
                PetEntry.TABLE_NAME+
                "("+
                PetEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                PetEntry.COLUMN_PET_NAME+" TEXT NOT NULL,"+
                PetEntry.COLUMN_PET_BREED+" TEXT,"+
                PetEntry.COLUMN_PET_GENDER+" INTEGER NOT NULL DEFAULT 0,"+
                PetEntry.COLUMN_PET_WEIGHT+" INTEGER NOT NULL DEFAULT 0"+
                ");";

        Log.v(LOG_TAG,SQL_CREATE_TABLE);
        db.execSQL(SQL_CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
