
package com.joshuahalvorson.petadoptionhelper.shelter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.joshuahalvorson.petadoptionhelper.animal.Pets;

import java.io.Serializable;

public class ShelterPetfinder implements Serializable {

    @SerializedName("@xmlns:xsi")
    @Expose
    private String xmlnsXsi;
    @SerializedName("lastOffset")
    @Expose
    private LastOffset lastOffset;
    @SerializedName("shelters")
    @Expose
    private Shelters shelters;
    @SerializedName("pets")
    @Expose
    private Pets pets;

    public String getXmlnsXsi() {
        return xmlnsXsi;
    }

    public void setXmlnsXsi(String xmlnsXsi) {
        this.xmlnsXsi = xmlnsXsi;
    }

    public LastOffset getLastOffset() {
        return lastOffset;
    }

    public void setLastOffset(LastOffset lastOffset) {
        this.lastOffset = lastOffset;
    }

    public Shelters getShelters() {
        return shelters;
    }

    public void setShelters(Shelters shelters) {
        this.shelters = shelters;
    }

    public Pets getPets() {
        return pets;
    }

    public void setPets(Pets pets) {
        this.pets = pets;
    }
}
