
package com.joshuahalvorson.petadoptionhelper.animal;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address1 implements Serializable {

    @SerializedName("$t")
    @Expose
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
