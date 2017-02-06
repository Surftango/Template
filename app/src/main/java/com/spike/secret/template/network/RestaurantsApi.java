package com.spike.secret.template.network;

import android.support.annotation.NonNull;

import com.spike.secret.template.model.Restaurant;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Shyam on 2/4/17.
 */
public interface RestaurantsApi {

    @GET("v2/restaurant/")
    Observable<List<Restaurant>> getRestaurants(@NonNull @Query("lat") double latitude, @NonNull @Query("lng") double longitude);
}
