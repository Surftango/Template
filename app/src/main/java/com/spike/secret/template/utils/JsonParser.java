package com.spike.secret.template.utils;

import org.json.JSONException;

/**
 * Interface that all Model objects, that derives data from the JSON response, should implement
 * <p>
 * Created by Shyam on 2/4/17.
 */

public interface JsonParser {

    void parse(String jsonString) throws JSONException;
}