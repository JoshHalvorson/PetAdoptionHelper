
package com.joshuahalvorson.petadoptionhelper.shelter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Longitude implements Serializable {

    @SerializedName("$t")
    @Expose
    private String longitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
