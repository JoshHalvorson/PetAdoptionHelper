
package com.joshuahalvorson.petadoptionhelper.breed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timestamp {

    @SerializedName("$t")
    @Expose
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
