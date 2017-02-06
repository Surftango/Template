package com.spike.secret.template.storage;

import android.support.annotation.NonNull;

import com.spike.secret.template.model.Restaurant;

import java.util.List;

import rx.Observable;

/**
 * Created by Shyam on 2/4/17.
 */

public interface RestaurantDataSource {

    Observable<List<Restaurant>> getAllFavourite();

    Observable<String> deleteAll();

    Observable<Restaurant> getFavourite(@NonNull String name);

    void deleteFavourite(@NonNull String name);

    Observable<String> addFavourite(@NonNull Restaurant favorite);

}
