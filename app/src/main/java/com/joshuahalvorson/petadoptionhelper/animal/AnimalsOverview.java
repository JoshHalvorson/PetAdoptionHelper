
package com.joshuahalvorson.petadoptionhelper.animal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AnimalsOverview implements Serializable {

    @SerializedName("@encoding")
    @Expose
    private String encoding;
    @SerializedName("@version")
    @Expose
    private String version;
    @SerializedName("petfinder")
    @Expose
    private AnimalPetfinder petfinder;

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public AnimalPetfinder getPetfinder() {
        return petfinder;
    }

    public void setPetfinder(AnimalPetfinder petfinder) {
        this.petfinder = petfinder;
    }

}
