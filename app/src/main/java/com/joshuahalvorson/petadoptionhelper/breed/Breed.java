
package com.joshuahalvorson.petadoptionhelper.breed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Breed {

    @SerializedName("$t")
    @Expose
    private String breed;

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

}
