package com.joshuahalvorson.petadoptionhelper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.joshuahalvorson.petadoptionhelper.animal.Pet;

import java.util.ArrayList;
import java.util.List;

public class TaggedAnimalsDbDao {

    private static SQLiteDatabase db;

    public static void initializeInstance(Context context){
        if(db == null){
            TaggedAnimalsDbHelper helper = new TaggedAnimalsDbHelper(context);
            db = helper.getWritableDatabase();
        }
    }

    public static void createAnimalEntry(Pet animal){
        if(db != null){
            ContentValues values = new ContentValues();
            values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_ID,
                    animal.getId().get$t());
            values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_NAME,
                    animal.getName().get$t());
            db.insert(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_TABLE_NAME,
                    null, values);
        }
    }

    public static List<String> readAllTaggedAnimals(){
        if(db != null){
            Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s;",
                    TaggedAnimalsDbContract.AnimalEntry.ANIMALS_TABLE_NAME),
                    null);
            List<String> ids = new ArrayList<>();
            String petId = "";
            while(cursor.moveToNext()){
                petId = getAnimalData(cursor);
                ids.add(petId);
            }
            cursor.close();
            return ids;
        }else{
            return new ArrayList<>();
        }
    }

    private static String getAnimalData(Cursor cursor){
        int index;
        index = cursor.getColumnIndexOrThrow(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_ID);
        String id = cursor.getString(index);
        return id;
    }
}
