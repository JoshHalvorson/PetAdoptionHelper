
package com.joshuahalvorson.petadoptionhelper.animal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pet implements Serializable {

    @SerializedName("options")
    @Expose
    private Options options;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("contact")
    @Expose
    private Contact contact;
    @SerializedName("age")
    @Expose
    private Age age;
    @SerializedName("size")
    @Expose
    private Size size;
    @SerializedName("media")
    @Expose
    private Media media;
    @SerializedName("id")
    @Expose
    private Id id;
    @SerializedName("shelterPetId")
    @Expose
    private ShelterPetId shelterPetId;
    @SerializedName("breeds")
    @Expose
    private Breeds breeds;
    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("sex")
    @Expose
    private Sex sex;
    @SerializedName("description")
    @Expose
    private Description description;
    @SerializedName("mix")
    @Expose
    private Mix mix;
    @SerializedName("shelterId")
    @Expose
    private ShelterId shelterId;
    @SerializedName("lastUpdate")
    @Expose
    private LastUpdate lastUpdate;
    @SerializedName("animal")
    @Expose
    private Animal animal;

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public ShelterPetId getShelterPetId() {
        return shelterPetId;
    }

    public void setShelterPetId(ShelterPetId shelterPetId) {
        this.shelterPetId = shelterPetId;
    }

    public Breeds getBreeds() {
        return breeds;
    }

    public void setBreeds(Breeds breeds) {
        this.breeds = breeds;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Mix getMix() {
        return mix;
    }

    public void setMix(Mix mix) {
        this.mix = mix;
    }

    public ShelterId getShelterId() {
        return shelterId;
    }

    public void setShelterId(ShelterId shelterId) {
        this.shelterId = shelterId;
    }

    public LastUpdate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LastUpdate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

}
