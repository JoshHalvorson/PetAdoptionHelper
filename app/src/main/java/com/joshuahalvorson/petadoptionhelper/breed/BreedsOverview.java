
package com.joshuahalvorson.petadoptionhelper.breed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BreedsOverview {

    @SerializedName("@encoding")
    @Expose
    private String encoding;
    @SerializedName("@version")
    @Expose
    private String version;
    @SerializedName("petfinder")
    @Expose
    private BreedPetfinder petfinder;

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

    public BreedPetfinder getPetfinder() {
        return petfinder;
    }

    public void setPetfinder(BreedPetfinder petfinder) {
        this.petfinder = petfinder;
    }

}
