
package com.joshuahalvorson.petadoptionhelper.animal;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShelterPetId implements Serializable {

    @SerializedName("$t")
    @Expose
    private String shelterPetId;

    public String getShelterPetId() {
        return shelterPetId;
    }

    public void setShelterPetId(String shelterPetId) {
        this.shelterPetId = shelterPetId;
    }

}
