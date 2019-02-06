
package com.joshuahalvorson.petadoptionhelper.animal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Age implements Serializable {

    @SerializedName("$t")
    @Expose
    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
