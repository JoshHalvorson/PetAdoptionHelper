package com.joshuahalvorson.petadoptionhelper.database;

import android.provider.BaseColumns;

public class TaggedAnimalsDbContract {
    public static class AnimalEntry implements BaseColumns{
        public static final String ANIMALS_TABLE_NAME = "animals";
        public static final String ANIMALS_COLUMN_ANIMAL_ID = "animal_id";
        public static final String ANIMALS_COLUMN_ANIMAL_NAME = "animal_name";

        public static final String SQL_CREATE_ANIMALS_TABLE = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY, " +
                        "%s INTEGER, " +
                        "%s TEXT);",
                ANIMALS_TABLE_NAME,
                _ID,
                ANIMALS_COLUMN_ANIMAL_ID,
                ANIMALS_COLUMN_ANIMAL_NAME
        );

        public static final String SQL_DELETE_ANIMALS_TABLE =
                "DROP TABLE IF EXISTS " + ANIMALS_TABLE_NAME + ";";

    }
}
