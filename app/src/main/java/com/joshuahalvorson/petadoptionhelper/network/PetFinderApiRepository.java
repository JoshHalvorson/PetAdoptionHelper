package com.joshuahalvorson.petadoptionhelper.network;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import com.joshuahalvorson.petadoptionhelper.Key;
import com.joshuahalvorson.petadoptionhelper.animal.AnimalPetfinder;
import com.joshuahalvorson.petadoptionhelper.animal.AnimalsOverview;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.shelter.ShelterPetfinder;
import com.joshuahalvorson.petadoptionhelper.shelter.SheltersOverview;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PetFinderApiRepository {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(PetFinderApiInterface.base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static PetFinderApiInterface client = retrofit.create(PetFinderApiInterface.class);

    private static AnimalsOverview animalsOverview;
    private static AnimalsOverview petDataOverview;
    private static SheltersOverview sheltersOverview;
    private static SheltersOverview petsInShelter;

    public static ShelterPetfinder shelterPetfinder;

    public static MutableLiveData<AnimalsOverview> getPetsInArea(
            int zipcode, String format, String offset){

        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PetFinderApiInterface.base_url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PetFinderApiInterface client = retrofit.create(PetFinderApiInterface.class);*/

        final MutableLiveData<AnimalsOverview> data = new MutableLiveData<>();
        Call<AnimalsOverview> call = client.getPetsInLocation(Key.API_KEY, zipcode, format, offset);
        call.enqueue(new Callback<AnimalsOverview>() {
            @Override
            public void onResponse(Call<AnimalsOverview> call, Response<AnimalsOverview> response) {
                animalsOverview = response.body();
                data.setValue(animalsOverview);
            }

            @Override
            public void onFailure(Call<AnimalsOverview> call, Throwable t) {
                Log.i("animalOverviewResult", t.getLocalizedMessage());
            }
        });
        return data;
    }

    public static MutableLiveData<SheltersOverview> getSheltersInArea(
            int zipcode, String format, String offset){

        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PetFinderApiInterface.base_url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PetFinderApiInterface client = retrofit.create(PetFinderApiInterface.class);*/

        final MutableLiveData<SheltersOverview> data = new MutableLiveData<>();
        Call<SheltersOverview> call =
                client.getSheltersInLocation(Key.API_KEY, zipcode, format, offset);
        call.enqueue(new Callback<SheltersOverview>() {
            @Override
            public void onResponse(Call<SheltersOverview> call,
                                   Response<SheltersOverview> response) {
                sheltersOverview = response.body();
                data.setValue(sheltersOverview);
            }

            @Override
            public void onFailure(Call<SheltersOverview> call, Throwable t) {
                Log.i("shelterOverviewResult", t.getLocalizedMessage());
            }
        });
        return data;
    }

    public static MutableLiveData<Pet> getAnimalData(
            String id, String format){

        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PetFinderApiInterface.base_url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PetFinderApiInterface client = retrofit.create(PetFinderApiInterface.class);*/

        final MutableLiveData<Pet> data = new MutableLiveData<>();
        Call<AnimalsOverview> call =
                client.getPetData(Key.API_KEY, id, format);
        call.enqueue(new Callback<AnimalsOverview>() {
            @Override
            public void onResponse(Call<AnimalsOverview> call, Response<AnimalsOverview> response) {
                petDataOverview = response.body();
                AnimalPetfinder petfinder = petDataOverview.getPetfinder();
                Pet pet = petfinder.getPet();
                data.setValue(pet);
            }

            @Override
            public void onFailure(Call<AnimalsOverview> call, Throwable t) {
                Log.i("petDataResult", t.getLocalizedMessage());
            }
        });
        return data;
    }

    public static MutableLiveData<List<Pet>> getPetsInShelter(
            String id, String format, String offset){

        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PetFinderApiInterface.base_url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PetFinderApiInterface client = retrofit.create(PetFinderApiInterface.class);*/

        final MutableLiveData<List<Pet>> data = new MutableLiveData<>();
        Call<SheltersOverview> call = client.getPetsInShelter(Key.API_KEY, id, format, offset);
        call.enqueue(new Callback<SheltersOverview>() {
            @Override
            public void onResponse(Call<SheltersOverview> call, Response<SheltersOverview> response) {
                petsInShelter = response.body();
                ShelterPetfinder shelterPetfinder = petsInShelter.getPetfinder();
                getShelterPetFinder(petsInShelter);
                data.setValue(shelterPetfinder.getPets().getPet());
            }

            @Override
            public void onFailure(Call<SheltersOverview> call, Throwable t) {
                Log.i("petsInShelterDataResult", t.getLocalizedMessage());
            }
        });
        return data;
    }

    public static ShelterPetfinder getShelterPetFinder(SheltersOverview sheltersOverview){
        shelterPetfinder = sheltersOverview.getPetfinder();
        return shelterPetfinder;
    }

}
