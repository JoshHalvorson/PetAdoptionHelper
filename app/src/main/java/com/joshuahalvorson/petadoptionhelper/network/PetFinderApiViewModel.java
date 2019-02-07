package com.joshuahalvorson.petadoptionhelper.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.joshuahalvorson.petadoptionhelper.animal.AnimalsOverview;
import com.joshuahalvorson.petadoptionhelper.animal.Pet;
import com.joshuahalvorson.petadoptionhelper.breed.BreedsOverview;
import com.joshuahalvorson.petadoptionhelper.shelter.ShelterPetfinder;
import com.joshuahalvorson.petadoptionhelper.shelter.SheltersOverview;
import java.util.List;

public class PetFinderApiViewModel extends ViewModel {

    private MutableLiveData<AnimalsOverview> data;
    private MutableLiveData<Pet> petData;
    private MutableLiveData<List<Pet>> petsInShelter;
    private MutableLiveData<SheltersOverview> sheltersOverviewMutableLiveData;
    private MutableLiveData<BreedsOverview> breeds;
    private MutableLiveData<SheltersOverview> shelterData;

    public LiveData<AnimalsOverview> getPetsInArea(int zipcode, String format, String offset,
                                                   String count, String animal, String breed,
                                                   String size, String sex, String age){
        data = PetFinderApiRepository.getPetsInArea(zipcode, format, offset, count,
                animal, breed, size, sex, age);
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

    public LiveData<List<Pet>> getPetsInShelter(String id, String format, String offset){
        petsInShelter = PetFinderApiRepository.getPetsInShelter(id, format, offset);
        return petsInShelter;
    }

    public LiveData<BreedsOverview> getBreedsForAnimal(String format, String animal){
        breeds = PetFinderApiRepository.getBreedsForAnimal(format, animal);
        return breeds;
    }

    public LiveData<SheltersOverview> getShelterData(String id, String format){
        shelterData = PetFinderApiRepository.getShelterData(id, format);
        return shelterData;
    }

    public static ShelterPetfinder getShelterPetFinder(){
        return PetFinderApiRepository.shelterPetfinder;
    }

}
