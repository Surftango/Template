package com.spike.secret.template.model;

/**
 * Created by Shyam on 2/5/17.
 */

public class LoginResponse {

    /*
        This is the response that we will be consuming

        {"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
        .eyJvcmlnX2lhdCI6MTQ4NjM2NTQ3NSwidXNlciI6eyJhdXRoX3ZlcnNpb24iOjIsImlzX3N0YWZmIjpmYWxzZSwiaWQiOjE3Mzg5NjU4LCJlbWFpbCI6InNoeWFtbW9oYW4uc3VnYXRoYW5AZ21haWwuY29tIn0sImV4cCI6MTQ4NjM4NzA3NX0
        .xJm9JbrpSR6dasFJPYvCrGNvXVuNgYPV7Y5xQ6M9UlE"
        }
     */

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
