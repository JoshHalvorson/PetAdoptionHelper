
package com.joshuahalvorson.petadoptionhelper.shelter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shelters {

    @SerializedName("shelter")
    @Expose
    private List<Shelter> shelter = null;

    public List<Shelter> getShelter() {
        return shelter;
    }

    public void setShelter(List<Shelter> shelter) {
        this.shelter = shelter;
    }

}
