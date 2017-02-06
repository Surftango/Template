package com.spike.secret.template.model;

import com.spike.secret.template.utils.JsonParser;

import org.json.JSONException;

import java.io.Serializable;

/**
 * Created by Shyam on 2/4/17.
 */
public class Restaurant implements Serializable{

    /*
    This is the response that we will be consuming
    {
    "is_time_surging": false,
    "max_order_size": null,
    "delivery_fee": 0,
    "max_composite_score": 10,
    "id": 7402,
    "menus": [
      {
        "is_catering": false,
        "subtitle": "",
        "id": 59220,
        "name": "#1 Chinese Kitchen (Lunch)"
      },
      {
        "is_catering": false,
        "subtitle": "",
        "id": 11301,
        "name": "#1 Chinese Kitchen"
      }
    ],
    "composite_score": 8,
    "status_type": "open",
    "is_only_catering": false,
    "status": "49 mins",
    "asap_time": 49,
    "description": "Chinese",
    "business": {
      "id": 6073,
      "name": "#1 Chinese Kitchen"
    },
    "tags": [
      "Chinese",
      "Catering"
    ],
    "yelp_review_count": 211,
    "business_id": 6073,
    "extra_sos_delivery_fee": 0,
    "yelp_rating": 4,
    "cover_img_url": "https://cdn.doordash.com/media/restaurant/cover/1-Chinese-Kitchen.png",
    "header_img_url": "https://doordash-static.s3.amazonaws.com/media/photos/aacc63c4-cedf-4756-8b72-70d41e1a48da-retina-large.jpg",
    "address": {
      "city": "San Jose",
      "state": "CA",
      "street": "5585 Snell Avenue",
      "lat": 37.2522157,
      "lng": -121.831435,
      "printable_address": "5585 Snell Avenue, San Jose, CA 95123, USA"
    },
    "price_range": 2,
    "slug": "-1-chinese-kitchen-san-jose",
    "name": "#1 Chinese Kitchen (San Jose)",
    "url": "/store/-1-chinese-kitchen-san-jose-7402/",
    "service_rate": 7,
    "promotion": null,
    "featured_category_description": null
  }
  */

    private String name;
    private String url;
    private Address address;
    private String cover_img_url;
    private float yelp_rating;
    private String status_type;
    private  int price_range;
    private String description;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice_range() {
        return price_range;
    }

    public void setPrice_range(int price_range) {
        this.price_range = price_range;
    }

    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCoverImage() {
        return cover_img_url;
    }

    public void setCoverImage(String coverImage) {
        this.cover_img_url = coverImage;
    }

    public double getRating() {
        return yelp_rating;
    }

    public void setRating(float rating) {
        this.yelp_rating = rating;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
