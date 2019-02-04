
package com.joshuahalvorson.petadoptionhelper.shelter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SheltersOverview {

    @SerializedName("@encoding")
    @Expose
    private String encoding;
    @SerializedName("@version")
    @Expose
    private String version;
    @SerializedName("petfinder")
    @Expose
    private ShelterPetfinder petfinder;

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

    public ShelterPetfinder getPetfinder() {
        return petfinder;
    }

    public void setPetfinder(ShelterPetfinder petfinder) {
        this.petfinder = petfinder;
    }

}
