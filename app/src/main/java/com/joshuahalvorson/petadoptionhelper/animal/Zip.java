
package com.joshuahalvorson.petadoptionhelper.animal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Zip implements Serializable {

    @SerializedName("$t")
    @Expose
    private String zip;

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

}
