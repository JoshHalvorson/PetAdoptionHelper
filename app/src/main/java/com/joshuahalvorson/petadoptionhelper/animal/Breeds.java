
package com.joshuahalvorson.petadoptionhelper.animal;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Breeds {

    @SerializedName("breed")
    @Expose
    private List<Breed> breed = null;

    public List<Breed> getBreed() {
        return breed;
    }

    public void setBreed(List<Breed> breed) {
        this.breed = breed;
    }

}
