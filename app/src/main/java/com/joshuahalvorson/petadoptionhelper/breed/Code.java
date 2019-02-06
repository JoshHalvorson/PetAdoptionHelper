
package com.joshuahalvorson.petadoptionhelper.breed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Code {

    @SerializedName("$t")
    @Expose
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
