
package com.joshuahalvorson.petadoptionhelper.breed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Version {

    @SerializedName("$t")
    @Expose
    private String $t;

    public String get$t() {
        return $t;
    }

    public void set$t(String $t) {
        this.$t = $t;
    }

}
