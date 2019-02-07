package com.joshuahalvorson.petadoptionhelper.database;

import android.provider.BaseColumns;

public class AnimalsDbContract {
    public static class AnimalEntry implements BaseColumns {
        public static final String ANIMALS_TABLE_NAME = "animals";
        public static final String ANIMALS_COLUMN_ANIMAL_ID = "animal_id";
        public static final String ANIMALS_COLUMN_ANIMAL_NAME = "animal_name";
        public static final String ANIMALS_COLUMN_ANIMAL_OPTIONS = "animal_options";
        public static final String ANIMALS_COLUMN_ANIMAL_CONTACT = "animal_contact";
        public static final String ANIMALS_COLUMN_ANIMAL_AGE = "animal_age";
        public static final String ANIMALS_COLUMN_ANIMAL_SIZE = "animal_size";
        public static final String ANIMALS_COLUMN_ANIMAL_IMAGE_URL = "animal_image_url";
        public static final String ANIMALS_COLUMN_ANIMAL_BREEDS = "animal_breeds";
        public static final String ANIMALS_COLUMN_ANIMAL_SEX = "animal_sex";
        public static final String ANIMALS_COLUMN_ANIMAL_DESCRIPTION = "animal_description";
        public static final String ANIMALS_COLUMN_ANIMAL_LAST_UPDATE = "animal_last_update";
        public static final String ANIMALS_COLUMN_ANIMAL_DISTANCE = "animal_distance";
        public static final String ANIMALS_COLUMN_ANIMAL_SHELTER = "animal_shelter";

        public static final String SQL_CREATE_ANIMALS_TABLE = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY, " +
                        "%s INTEGER, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT);",
                ANIMALS_TABLE_NAME,
                _ID,
                ANIMALS_COLUMN_ANIMAL_ID,
                ANIMALS_COLUMN_ANIMAL_NAME,
                ANIMALS_COLUMN_ANIMAL_OPTIONS,
                ANIMALS_COLUMN_ANIMAL_CONTACT,
                ANIMALS_COLUMN_ANIMAL_AGE,
                ANIMALS_COLUMN_ANIMAL_SIZE,
                ANIMALS_COLUMN_ANIMAL_IMAGE_URL,
                ANIMALS_COLUMN_ANIMAL_BREEDS,
                ANIMALS_COLUMN_ANIMAL_SEX,
                ANIMALS_COLUMN_ANIMAL_DESCRIPTION,
                ANIMALS_COLUMN_ANIMAL_LAST_UPDATE,
                ANIMALS_COLUMN_ANIMAL_DISTANCE,
                ANIMALS_COLUMN_ANIMAL_SHELTER
        );

        public static final String SQL_DELETE_ANIMALS_TABLE =
                "DROP TABLE IF EXISTS " + ANIMALS_TABLE_NAME + ";";

        public static final String ANIMALS_HISTORY_TABLE_NAME = "animals_history";
        public static final String ANIMALS_HISTORY_COLUMN_ANIMAL_ID = "animal_id";
        public static final String ANIMALS_HISTORY_COLUMN_ANIMAL_NAME = "animal_name";
        public static final String ANIMALS_HISTORY_COLUMN_ANIMAL_OPTIONS = "animal_options";
        public static final String ANIMALS_HISTORY_COLUMN_ANIMAL_CONTACT = "animal_contact";
        public static final String ANIMALS_HISTORY_COLUMN_ANIMAL_AGE = "animal_age";
        public static final String ANIMALS_HISTORY_COLUMN_ANIMAL_SIZE = "animal_size";
        public static final String ANIMALS_HISTORY_COLUMN_ANIMAL_IMAGE_URL = "animal_image_url";
        public static final String ANIMALS_HISTORY_COLUMN_ANIMAL_BREEDS = "animal_breeds";
        public static final String ANIMALS_HISTORY_COLUMN_ANIMAL_SEX = "animal_sex";
        public static final String ANIMALS_HISTORY_COLUMN_ANIMAL_DESCRIPTION = "animal_description";
        public static final String ANIMALS_HISTORY_COLUMN_ANIMAL_LAST_UPDATE = "animal_last_update";
        public static final String ANIMALS_HISTORY_COLUMN_ANIMAL_DISTANCE = "animal_distance";
        public static final String ANIMALS_HISTORY_COLUMN_ANIMAL_SHELTER = "animal_shelter";

        public static final String SQL_CREATE_ANIMALS_HISTORY_TABLE = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY, " +
                        "%s INTEGER, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT);",
                ANIMALS_HISTORY_TABLE_NAME,
                _ID,
                ANIMALS_HISTORY_COLUMN_ANIMAL_ID,
                ANIMALS_HISTORY_COLUMN_ANIMAL_NAME,
                ANIMALS_HISTORY_COLUMN_ANIMAL_OPTIONS,
                ANIMALS_HISTORY_COLUMN_ANIMAL_CONTACT,
                ANIMALS_HISTORY_COLUMN_ANIMAL_AGE,
                ANIMALS_HISTORY_COLUMN_ANIMAL_SIZE,
                ANIMALS_HISTORY_COLUMN_ANIMAL_IMAGE_URL,
                ANIMALS_HISTORY_COLUMN_ANIMAL_BREEDS,
                ANIMALS_HISTORY_COLUMN_ANIMAL_SEX,
                ANIMALS_HISTORY_COLUMN_ANIMAL_DESCRIPTION,
                ANIMALS_HISTORY_COLUMN_ANIMAL_LAST_UPDATE,
                ANIMALS_HISTORY_COLUMN_ANIMAL_DISTANCE,
                ANIMALS_HISTORY_COLUMN_ANIMAL_SHELTER
                );

        public static final String SQL_DELETE_ANIMALS__HISTORY_TABLE =
                "DROP TABLE IF EXISTS " + ANIMALS_HISTORY_TABLE_NAME + ";";
    }

}
