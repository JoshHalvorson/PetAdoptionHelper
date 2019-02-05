package com.joshuahalvorson.petadoptionhelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaggedAnimalsDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "taggedAnimals.db";

    public TaggedAnimalsDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TaggedAnimalsDbContract.AnimalEntry.SQL_CREATE_ANIMALS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TaggedAnimalsDbContract.AnimalEntry.SQL_DELETE_ANIMALS_TABLE);
        this.onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.onUpgrade(db, oldVersion, newVersion);
    }
}
