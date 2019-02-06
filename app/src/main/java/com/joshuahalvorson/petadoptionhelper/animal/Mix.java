
package com.joshuahalvorson.petadoptionhelper.animal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Mix implements Serializable {

    @SerializedName("$t")
    @Expose
    private String animalMix;

    public String getAnimalMix() {
        return animalMix;
    }

    public void setAnimalMix(String animalMix) {
        this.animalMix = animalMix;
    }

}
