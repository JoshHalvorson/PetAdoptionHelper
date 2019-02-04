package com.joshuahalvorson.petadoptionhelper.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.joshuahalvorson.petadoptionhelper.animal.AnimalsOverview;
import com.joshuahalvorson.petadoptionhelper.shelter.SheltersOverview;

public class PetFinderApiViewModel extends ViewModel {

    private MutableLiveData<AnimalsOverview> data;
    private MutableLiveData<SheltersOverview> sheltersOverviewMutableLiveData;

    public LiveData<AnimalsOverview> getPetsInArea(int zipcode, String format){
        data = PetFinderApiRepository.getPetsInArea(zipcode, format);
        return data;
    }

    public LiveData<SheltersOverview> getSheltersInArea(int zipcode, String format){
        sheltersOverviewMutableLiveData = PetFinderApiRepository.getSheltersInArea(zipcode, format);
        return sheltersOverviewMutableLiveData;
    }

}
