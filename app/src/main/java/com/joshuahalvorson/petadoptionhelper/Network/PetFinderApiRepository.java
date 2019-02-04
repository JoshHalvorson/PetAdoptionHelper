package com.joshuahalvorson.petadoptionhelper.Network;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.joshuahalvorson.petadoptionhelper.Key;
import com.joshuahalvorson.petadoptionhelper.animal.AnimalsOverview;
import com.joshuahalvorson.petadoptionhelper.animal.Pets;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

    public static MutableLiveData<AnimalsOverview> getPetsInArea(int zipcode, String format){

        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();*/

        final MutableLiveData<AnimalsOverview> data = new MutableLiveData<>();
        Call<AnimalsOverview> call = client.getPetsInLocation(Key.API_KEY, zipcode, format);
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

}
