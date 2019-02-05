package com.joshuahalvorson.petadoptionhelper.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.joshuahalvorson.petadoptionhelper.animal.AnimalsOverview;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.shelter.SheltersOverview;

import java.util.List;

public class PetFinderApiViewModel extends ViewModel {

    private MutableLiveData<AnimalsOverview> data;
    private MutableLiveData<Pet> petData;
    private MutableLiveData<List<Pet>> petsInShelter;
    private MutableLiveData<SheltersOverview> sheltersOverviewMutableLiveData;

    public LiveData<AnimalsOverview> getPetsInArea(int zipcode, String format, String offset){
        data = PetFinderApiRepository.getPetsInArea(zipcode, format, offset);
        return data;
    }

    public LiveData<SheltersOverview> getSheltersInArea(int zipcode, String format, String offset){
        sheltersOverviewMutableLiveData =
                PetFinderApiRepository.getSheltersInArea(zipcode, format, offset);
        return sheltersOverviewMutableLiveData;
    }

    public LiveData<Pet> getAnimalData(String id, String format){
        petData = PetFinderApiRepository.getAnimalData(id, format);
        return petData;
    }

    public LiveData<List<Pet>> getPetsInShelter(String id, String format){
        petsInShelter = PetFinderApiRepository.getPetsInShelter(id, format);
        return petsInShelter;
    }

}
