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
            @Query("offset") String offset
    );

    @GET("shelter.find")
    Call<SheltersOverview> getSheltersInLocation(
            @Query("key") String key,
            @Query("location") int zip,
            @Query("format") String format,
            @Query("offset") String offset
    );

}
