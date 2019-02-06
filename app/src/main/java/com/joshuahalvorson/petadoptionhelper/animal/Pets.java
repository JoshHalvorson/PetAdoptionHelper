
package com.joshuahalvorson.petadoptionhelper.animal;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pets implements Serializable {

    @SerializedName("pet")
    @Expose
    private List<Pet> pet = null;

    public List<Pet> getPet() {
        return pet;
    }

    public void setPet(List<Pet> pet) {
        this.pet = pet;
    }

}
