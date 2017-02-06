package com.spike.secret.template.model;

import java.io.Serializable;

/**
 * Created by Shyam on 2/5/17.
 */

public class Consumer implements Serializable {
    /*
       This is the response that we will be consuming
       {
        "last_name": "Sugathan",
        "social_accounts": [],
        "object_type": "consumer.consumer",
        "is_repeat_consumer": false,
        "default_card": null,
        "referral_code": "Shyammohan-Sugathan-1842",
        "id": 17370247,
        "first_name": "Shyammohan",
        "order_count": 0,
        "receive_marketing_push_notifications": true,
        "default_address": null,
        "email": "shyammohan.sugathan@gmail.com",
        "channel": "",
        "phone_number": "(480) 382-8054",
        "has_usable_password": true,
        "referrer_amount": 700,
        "account_credits": 0,
        "receive_text_notifications": false,
        "default_country": "US",
        "receive_push_notifications": false,
        "has_accepted_latest_terms_of_service": true,
        "latest_version_of_terms_of_service": "1.0",
        "social_data": [],
        "only_ordered_once": false,
        "is_guest": false,
        "default_substitution_preference": ""
        }
     */

    private String last_name;
    private String first_name;
    private String email;
    private String phone_number;

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
