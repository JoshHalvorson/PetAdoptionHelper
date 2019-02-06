
package com.joshuahalvorson.petadoptionhelper.animal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Id implements Serializable {

    @SerializedName("$t")
    @Expose
    private String animalId;

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

}
