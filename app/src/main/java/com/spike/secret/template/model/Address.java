package com.spike.secret.template.model;

import java.io.Serializable;

/**
 * Created by Shyam on 2/4/17.
 */
public class Address implements Serializable {

    /*
    This is the response that we will be consuming

    "address": {
      "city": "San Jose",
      "state": "CA",
      "street": "5585 Snell Avenue",
      "lat": 37.2522157,
      "lng": -121.831435,
      "printable_address": "5585 Snell Avenue, San Jose, CA 95123, USA"
    }
    */

    private String city;
    private String state;
    private String street;
    private double lat;
    private double lng;
    private String printable_address;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPrintable_address() {
        return printable_address;
    }

    public void setPrintable_address(String printable_address) {
        this.printable_address = printable_address;
    }
}
