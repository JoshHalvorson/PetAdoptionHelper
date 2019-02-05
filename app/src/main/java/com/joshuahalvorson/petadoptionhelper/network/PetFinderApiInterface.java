package com.joshuahalvorson.petadoptionhelper.network;

import com.joshuahalvorson.petadoptionhelper.animal.AnimalsOverview;
import com.joshuahalvorson.petadoptionhelper.shelter.SheltersOverview;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PetFinderApiInterface {

    String base_url = "http://api.petfinder.com/";

    @GET("pet.find")
    Call<AnimalsOverview> getPetsInLocation(
            @Query("key") String key,
            @Query("location") int zip,
            @Query("format") String format,
            @Query("offset") String offset,

            @Query("offset") String animal,
            @Query("offset") String breed,
            @Query("offset") String size,
            @Query("offset") String sex,
            @Query("offset") String age
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

}
