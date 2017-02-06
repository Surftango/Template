package com.spike.secret.template.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shyam on 2/5/17.
 */

public class Restaurants implements Serializable {

    private List<Restaurant> restaurants;

    public Restaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
