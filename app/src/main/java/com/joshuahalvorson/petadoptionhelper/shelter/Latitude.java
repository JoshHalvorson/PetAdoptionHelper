
package com.joshuahalvorson.petadoptionhelper.shelter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Latitude implements Serializable {

    @SerializedName("$t")
    @Expose
    private String latitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}
