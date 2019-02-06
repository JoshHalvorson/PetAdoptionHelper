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
        sOptions = pet.getOptions().getOption().toString();
        sContact = pet.getContact().getCity().getCity();
        sAge = pet.getAge().getAge();
        sSize = pet.getSize().getAnimalSize();
        sMedia = pet.getMedia().getPhotos().getPhoto().get(2).getImageUrl();
        sId = pet.getId().getAnimalId();
        sBreeds = pet.getBreeds().getBreed().toString();
        sName = pet.getName().getAnimalName();
        sSex = pet.getSex().getAnimalSex();
        sDescription = pet.getDescription().getAnimalDescription();
        sLastUpdate = pet.getLastUpdate().getLastUpdate();
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
