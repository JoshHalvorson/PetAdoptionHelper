
package com.joshuahalvorson.petadoptionhelper.animal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AnimalPetfinder implements Serializable {

    @SerializedName("@xmlns:xsi")
    @Expose
    private String xmlnsXsi;
    @SerializedName("lastOffset")
    @Expose
    private LastOffset lastOffset;
    @SerializedName("pets")
    @Expose
    private Pets pets;
    @SerializedName("pet")
    @Expose
    private Pet pet;

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

    public Pets getPets() {
        return pets;
    }

    public void setPets(Pets pets) {
        this.pets = pets;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
