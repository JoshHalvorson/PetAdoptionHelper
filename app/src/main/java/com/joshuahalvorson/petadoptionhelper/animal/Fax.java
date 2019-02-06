
package com.joshuahalvorson.petadoptionhelper.animal;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Fax implements Serializable {
    @SerializedName("$t")
    @Expose
    private String fax;

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
}
