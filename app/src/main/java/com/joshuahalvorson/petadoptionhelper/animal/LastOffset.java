
package com.joshuahalvorson.petadoptionhelper.animal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LastOffset implements Serializable {

    @SerializedName("$t")
    @Expose
    private String lastOffset;

    public String getLastOffset() {
        return lastOffset;
    }

    public void setLastOffset(String lastOffset) {
        this.lastOffset = lastOffset;
    }

}
