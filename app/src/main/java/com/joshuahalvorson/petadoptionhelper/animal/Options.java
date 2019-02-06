
package com.joshuahalvorson.petadoptionhelper.animal;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Options implements Serializable {

    @SerializedName("option")
    @Expose
    private Object option = null;

    public Object getOption() {
        return option;
    }

    public void setOption(Object option) {
        this.option = option;
    }

}
