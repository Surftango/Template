package com.spike.secret.template.model;

/**
 * Created by Shyam on 2/5/17.
 */

public class LoginRequest {
    final String email;
    final String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
