package com.joshuahalvorson.petadoptionhelper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.joshuahalvorson.petadoptionhelper.animal.Animal;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.animal.StringPet;
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
            if (checkAnimalExists("animals", "animal_id", animal.getId().getAnimalId())){
                ContentValues values = new ContentValues();
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_ID,
                        animal.getId().getAnimalId());
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_NAME,
                        animal.getName().getAnimalName());
                if(animal.getOptions().getOption() != null){
                    values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_OPTIONS,
                            animal.getOptions().getOption().toString());
                }

                String phone = "Phone unknown";
                if (animal.getContact().getPhone().getPhone() != null){
                    phone = animal.getContact().getPhone().getPhone();
                }

                String email = "Email unkown";
                if (animal.getContact().getEmail().getEmail() != null){
                    email = animal.getContact().getEmail().getEmail();
                }

                String address = "Address unknown";
                if(animal.getContact().getAddress1().getAddress() != null){
                    address = animal.getContact().getAddress1().getAddress();
                }else if(animal.getContact().getAddress2().getAddress() != null){
                    address = animal.getContact().getAddress2().getAddress();
                }

                String city = "City unknown";
                if(animal.getContact().getCity().getCity() != null){
                    city = animal.getContact().getCity().getCity();
                }

                String state = "State unknown";
                if(animal.getContact().getState().getState() != null){
                    state = animal.getContact().getState().getState();
                }

                String zip = "Zip unknown";
                if(animal.getContact().getZip().getZip() != null){
                    zip = animal.getContact().getZip().getZip();
                }

                String contact = "Phone: " + phone + "\n" +
                        "Email: " + email + "\n" +
                        "Location: " + address + ", " + city + ", " + state + " " + zip;

                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_CONTACT,
                        contact);

                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_AGE,
                        animal.getAge().getAge());
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_SIZE,
                        animal.getSize().getAnimalSize());
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_IMAGE_URL,
                        animal.getMedia().getPhotos().getPhoto().get(2).getImageUrl());
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_BREEDS,
                        animal.getBreeds().getBreed().toString());
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_SEX,
                        animal.getSex().getAnimalSex());
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_DESCRIPTION,
                        animal.getDescription().getAnimalDescription());
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_LAST_UPDATE,
                        animal.getLastUpdate().getLastUpdate());
                db.insert(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_TABLE_NAME,
                        null, values);
            }
        }
    }

    public static void createAnimalHistoryEntry(Pet animal){
        if(db != null){
            if (!checkAnimalExists("animals_history", "animal_id", animal.getId().getAnimalId())){
                ContentValues values = new ContentValues();
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_ID,
                        animal.getId().getAnimalId());
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_NAME,
                        animal.getName().getAnimalName());
                if(animal.getOptions().getOption() != null){
                    values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_OPTIONS,
                            animal.getOptions().getOption().toString());
                }

                String phone = "Phone unknown";
                if (animal.getContact().getPhone().getPhone() != null){
                    phone = animal.getContact().getPhone().getPhone();
                }

                String email = "Email unkown";
                if (animal.getContact().getEmail().getEmail() != null){
                    email = animal.getContact().getEmail().getEmail();
                }

                String address = "Address unknown";
                if(animal.getContact().getAddress1().getAddress() != null){
                    address = animal.getContact().getAddress1().getAddress();
                }else if(animal.getContact().getAddress2().getAddress() != null){
                    address = animal.getContact().getAddress2().getAddress();
                }

                String city = "City unknown";
                if(animal.getContact().getCity().getCity() != null){
                    city = animal.getContact().getCity().getCity();
                }

                String state = "State unknown";
                if(animal.getContact().getState().getState() != null){
                    state = animal.getContact().getState().getState();
                }

                String zip = "Zip unknown";
                if(animal.getContact().getZip().getZip() != null){
                    zip = animal.getContact().getZip().getZip();
                }

                String contact = "Phone: " + phone + "\n" +
                        "Email: " + email + "\n" +
                        "Location: " + address + ", " + city + ", " + state + " " + zip;

                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_CONTACT,
                        contact);

                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_AGE,
                        animal.getAge().getAge());
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_SIZE,
                        animal.getSize().getAnimalSize());
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_IMAGE_URL,
                        animal.getMedia().getPhotos().getPhoto().get(2).getImageUrl());
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_BREEDS,
                        animal.getBreeds().getBreed().toString());
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_SEX,
                        animal.getSex().getAnimalSex());
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_DESCRIPTION,
                        animal.getDescription().getAnimalDescription());
                values.put(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_LAST_UPDATE,
                        animal.getLastUpdate().getLastUpdate());
                db.insert(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_TABLE_NAME,
                        null, values);
            }
        }
    }

    public static List<StringPet> readAllTaggedAnimals(){
        if(db != null){
            Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s;",
                    TaggedAnimalsDbContract.AnimalEntry.ANIMALS_TABLE_NAME),
                    null);
            List<StringPet> pets = new ArrayList<>();
            while(cursor.moveToNext()){
                StringPet pet = getAnimalData(cursor);
                pets.add(pet);
            }
            cursor.close();
            return pets;
        }else{
            return new ArrayList<>();
        }
    }

    public static List<StringPet> readAllAnimalsHistory(){
        if(db != null){
            Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s;",
                    TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_TABLE_NAME),
                    null);
            List<StringPet> pets = new ArrayList<>();
            while(cursor.moveToNext()){
                StringPet pet = getAnimalHistoryData(cursor);
                pets.add(pet);
            }
            cursor.close();
            return pets;
        }else{
            return new ArrayList<>();
        }
    }

    public static void deleteAnimalEntry(StringPet pet){
        if(db != null){
            String where = String.format("%s = %s",
                    TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_ID, pet.getsId());
            db.delete(TaggedAnimalsDbContract.AnimalEntry.ANIMALS_TABLE_NAME, where, null);
        }
    }

    public static boolean checkAnimalExists(String tableName, String dbfield, String fieldValue) {
        if(db != null) {
            Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s = %s;",
                    tableName, dbfield, fieldValue), null);

            if (cursor.getCount() <= 0) {
                cursor.close();
                return false;
            }
            cursor.close();
            return true;
        }
        return false;
    }

    private static StringPet getAnimalData(Cursor cursor){
        int index;
        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_ID);
        String id = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_OPTIONS);
        String options = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_CONTACT);
        String contact = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_AGE);
        String age = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_SIZE);
        String size  = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_IMAGE_URL);
        String imageUrl = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_BREEDS);
        String breeds = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_NAME);
        String name = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_SEX);
        String sex = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_DESCRIPTION);
        String desc = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_COLUMN_ANIMAL_LAST_UPDATE);
        String lastUpdate = cursor.getString(index);

        return new StringPet(
                options, contact, age, size, imageUrl, id, breeds, name, sex, desc, lastUpdate);
    }

    private static StringPet getAnimalHistoryData(Cursor cursor){
        int index;
        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_ID);
        String id = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_OPTIONS);
        String options = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_CONTACT);
        String contact = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_AGE);
        String age = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_SIZE);
        String size  = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_IMAGE_URL);
        String imageUrl = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_BREEDS);
        String breeds = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_NAME);
        String name = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_SEX);
        String sex = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_DESCRIPTION);
        String desc = cursor.getString(index);

        index = cursor.getColumnIndexOrThrow(
                TaggedAnimalsDbContract.AnimalEntry.ANIMALS_HISTORY_COLUMN_ANIMAL_LAST_UPDATE);
        String lastUpdate = cursor.getString(index);

        return new StringPet(
                options, contact, age, size, imageUrl, id, breeds, name, sex, desc, lastUpdate);
    }
}
