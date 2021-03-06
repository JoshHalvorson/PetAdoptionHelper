package com.joshuahalvorson.petadoptionhelper.network;

import com.joshuahalvorson.petadoptionhelper.animal.AnimalsOverview;
import com.joshuahalvorson.petadoptionhelper.breed.BreedsOverview;
import com.joshuahalvorson.petadoptionhelper.shelter.SheltersOverview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PetFinderApiInterface {

    String base_url = "https://api.petfinder.com/";

    @GET("pet.find")
    Call<AnimalsOverview> getPetsInLocation(
            @Query("key") String key,
            @Query("location") int zip,
            @Query("format") String format,
            @Query("offset") String offset,
            @Query("count") String count,

            @Query("animal") String animal,
            @Query("breed") String breed,
            @Query("size") String size,
            @Query("sex") String sex,
            @Query("age") String age
    );

    @GET("shelter.find")
    Call<SheltersOverview> getSheltersInLocation(
            @Query("key") String key,
            @Query("location") int zip,
            @Query("format") String format,
            @Query("offset") String offset
    );

    @GET("pet.get")
    Call<AnimalsOverview> getPetData(
            @Query("key") String key,
            @Query("id") String id,
            @Query("format") String format
    );

    @GET("shelter.getPets")
    Call<SheltersOverview> getPetsInShelter(
            @Query("key") String key,
            @Query("id") String id,
            @Query("format") String format,
            @Query("offset") String offset
    );

    @GET("shelter.get")
    Call<SheltersOverview> getShelterData(
            @Query("key") String key,
            @Query("id") String id,
            @Query("format") String format
    );

    @GET("breed.list")
    Call<BreedsOverview> getBreedsForAnimal(
            @Query("key") String key,
            @Query("format") String format,
            @Query("animal") String animal
    );

}
