
package com.joshuahalvorson.petadoptionhelper.animal;

import java.util.HashMap;
import java.util.Map;

public class StringPetFromJson {

    private AnimalId animalId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public AnimalId getAnimalId() {
        return animalId;
    }

    public void setAnimalId(AnimalId animalId) {
        this.animalId = animalId;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
