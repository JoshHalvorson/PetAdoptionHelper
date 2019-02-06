
package com.joshuahalvorson.petadoptionhelper.animal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Option implements Serializable {

    @SerializedName("$t")
    @Expose
    private String animalOptions;

    public String getAnimalOptions() {
        return animalOptions;
    }

    public void setAnimalOptions(String animalOptions) {
        this.animalOptions = animalOptions;
    }

}
