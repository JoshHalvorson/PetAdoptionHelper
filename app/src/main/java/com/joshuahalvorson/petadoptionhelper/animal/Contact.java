
package com.joshuahalvorson.androidmenusdesign.animal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact {

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
    @SerializedName("address1")
    @Expose
    private Address1 address1;

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

    public Address1 getAddress1() {
        return address1;
    }

    public void setAddress1(Address1 address1) {
        this.address1 = address1;
    }

}
