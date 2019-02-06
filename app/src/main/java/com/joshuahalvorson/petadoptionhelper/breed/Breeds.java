
package com.joshuahalvorson.petadoptionhelper.breed;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Breeds {

    @SerializedName("breed")
    @Expose
    private List<Breed> breed = null;
    @SerializedName("@animal")
    @Expose
    private String animal;

    public List<Breed> getBreed() {
        return breed;
    }

    public void setBreed(List<Breed> breed) {
        this.breed = breed;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

}
