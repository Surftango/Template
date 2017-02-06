package com.spike.secret.template.network;

import android.support.annotation.NonNull;

import com.spike.secret.template.model.Consumer;
import com.spike.secret.template.model.LoginRequest;
import com.spike.secret.template.model.LoginResponse;
import com.spike.secret.template.model.Restaurant;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Shyam on 2/4/17.
 */
public interface ConsumerApi {

    @GET("v2/consumer/me/")
    Observable<Consumer> getConsumerProfile(@Header("Authorization") String authorization);

    @POST("v2/auth/token/")
    Observable<LoginResponse> getAuthToken(@Body LoginRequest req);

}
