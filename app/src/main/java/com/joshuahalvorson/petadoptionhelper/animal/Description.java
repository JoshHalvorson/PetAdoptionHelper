
package com.joshuahalvorson.petadoptionhelper.animal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Description implements Serializable {

    @SerializedName("$t")
    @Expose
    private String animalDescription;

    public String getAnimalDescription() {
        return animalDescription;
    }

    public void setAnimalDescription(String animalDescription) {
        this.animalDescription = animalDescription;
    }

}
