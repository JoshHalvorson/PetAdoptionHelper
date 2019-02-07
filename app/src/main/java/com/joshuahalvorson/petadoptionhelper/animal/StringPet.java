package com.joshuahalvorson.petadoptionhelper.animal;

import java.io.Serializable;

public class StringPet implements Serializable {

    private String sOptions, sContact, sName, sId, sSex, sSize, sBreeds, sMedia, sAge,
            sDescription, sLastUpdate;

    public StringPet(String options, String contact, String age, String size, String media, String id,
               String breeds, String name, String sex, String description, String lastUpdate) {
        sOptions = options;
        sContact = contact;
        sAge = age;
        sSize = size;
        sMedia = media;
        sId = id;
        sBreeds = breeds;
        sName = name;
        sSex = sex;
        sDescription = description;
        sLastUpdate = lastUpdate;
    }

    public StringPet(Pet pet){
        if(pet.getOptions() != null){
            sOptions = pet.getOptions().getOption().toString();
        }
        if(pet.getContact().getCity() != null){
            sContact = pet.getContact().getCity().getCity();
        }
        if(pet.getAge() != null){
            sAge = pet.getAge().getAge();
        }
        if(pet.getSize() != null){
            sSize = pet.getSize().getAnimalSize();
        }
        if(pet.getMedia().getPhotos().getPhoto() != null){
            sMedia = pet.getMedia().getPhotos().getPhoto().get(2).getImageUrl();
        }
        if(pet.getId() != null){
            sId = pet.getId().getAnimalId();
        }
        if(pet.getBreeds().getBreed() != null){
            sBreeds = pet.getBreeds().getBreed().toString();
        }
        if(pet.getName() != null){
            sName = pet.getName().getAnimalName();
        }
        if(pet.getSex() != null){
            sSex = pet.getSex().getAnimalSex();
        }
        if(pet.getDescription() != null){
            sDescription = pet.getDescription().getAnimalDescription();
        }
        if(pet.getLastUpdate() != null){
            sLastUpdate = pet.getLastUpdate().getLastUpdate();
        }
    }

    public StringPet(AnimalId animal){
        if(animal.getOptions() != null){
            sOptions = animal.getOptions();
        }
        if(animal.getContact() != null){
            sContact = animal.getContact();
        }
        if(animal.getAge() != null){
            sAge = animal.getAge();
        }
        if(animal.getSize() != null){
            sSize = animal.getSize();
        }
        if(animal.getMedia() != null){
            sMedia = animal.getMedia();
        }
        if(animal.getId() != null){
            sId = animal.getId();
        }
        if(animal.getBreeds() != null){
            sBreeds = animal.getBreeds();
        }
        if(animal.getName() != null){
            sName = animal.getName();
        }
        if(animal.getSex() != null){
            sSex = animal.getSex();
        }
        if(animal.getDescription() != null){
            sDescription = animal.getDescription();
        }
        if(animal.getLastUpdate() != null){
            sLastUpdate = animal.getLastUpdate();
        }
    }

    public String getsOptions() {
        return sOptions;
    }

    public void setsOptions(String sOptions) {
        this.sOptions = sOptions;
    }

    public String getsContact() {
        return sContact;
    }

    public void setsContact(String sContact) {
        this.sContact = sContact;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsSex() {
        return sSex;
    }

    public void setsSex(String sSex) {
        this.sSex = sSex;
    }

    public String getsSize() {
        return sSize;
    }

    public void setsSize(String sSize) {
        this.sSize = sSize;
    }

    public String getsBreeds() {
        return sBreeds;
    }

    public void setsBreeds(String sBreeds) {
        this.sBreeds = sBreeds;
    }

    public String getsMedia() {
        return sMedia;
    }

    public void setsMedia(String sMedia) {
        this.sMedia = sMedia;
    }

    public String getsAge() {
        return sAge;
    }

    public void setsAge(String sAge) {
        this.sAge = sAge;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public String getsLastUpdate() {
        return sLastUpdate;
    }

    public void setsLastUpdate(String sLastUpdate) {
        this.sLastUpdate = sLastUpdate;
    }
}
