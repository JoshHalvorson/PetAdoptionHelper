package com.joshuahalvorson.petadoptionhelper.Network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.joshuahalvorson.petadoptionhelper.animal.AnimalsOverview;
import com.joshuahalvorson.petadoptionhelper.animal.Pets;

import java.util.List;

public class PetFinderApiViewModel extends ViewModel {

    private MutableLiveData<AnimalsOverview> data;

    public LiveData<AnimalsOverview> getPetsInArea(int zipcode, String format){
        data = PetFinderApiRepository.getPetsInArea(zipcode, format);
        return data;
    }

}
