
package com.joshuahalvorson.petadoptionhelper.animal;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Breeds implements Serializable {

    @SerializedName("breed")
    @Expose
    private Object breed = null;

    public Object getBreed() {
        return breed;
    }

    public void setBreed(Object breed) {
        this.breed = breed;
    }

}
