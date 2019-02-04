
package com.joshuahalvorson.petadoptionhelper.animal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnimalsOverview {

    @SerializedName("@encoding")
    @Expose
    private String encoding;
    @SerializedName("@version")
    @Expose
    private String version;
    @SerializedName("petfinder")
    @Expose
    private Petfinder petfinder;

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

    public Petfinder getPetfinder() {
        return petfinder;
    }

    public void setPetfinder(Petfinder petfinder) {
        this.petfinder = petfinder;
    }

}
