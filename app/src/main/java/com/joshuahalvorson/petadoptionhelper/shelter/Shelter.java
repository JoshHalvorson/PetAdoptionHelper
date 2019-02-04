
package com.joshuahalvorson.petadoptionhelper.shelter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shelter {

    @SerializedName("country")
    @Expose
    private Country country;
    @SerializedName("longitude")
    @Expose
    private Longitude longitude;
    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("phone")
    @Expose
    private Phone phone;
    @SerializedName("state")
    @Expose
    private State state;
    @SerializedName("address2")
    @Expose
    private Address2 address2;
    @SerializedName("email")
    @Expose
    private Email email;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("zip")
    @Expose
    private Zip zip;
    @SerializedName("fax")
    @Expose
    private Fax fax;
    @SerializedName("latitude")
    @Expose
    private Latitude latitude;
    @SerializedName("id")
    @Expose
    private Id id;
    @SerializedName("address1")
    @Expose
    private Address1 address1;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Longitude getLongitude() {
        return longitude;
    }

    public void setLongitude(Longitude longitude) {
        this.longitude = longitude;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Address2 getAddress2() {
        return address2;
    }

    public void setAddress2(Address2 address2) {
        this.address2 = address2;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Zip getZip() {
        return zip;
    }

    public void setZip(Zip zip) {
        this.zip = zip;
    }

    public Fax getFax() {
        return fax;
    }

    public void setFax(Fax fax) {
        this.fax = fax;
    }

    public Latitude getLatitude() {
        return latitude;
    }

    public void setLatitude(Latitude latitude) {
        this.latitude = latitude;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Address1 getAddress1() {
        return address1;
    }

    public void setAddress1(Address1 address1) {
        this.address1 = address1;
    }

}
