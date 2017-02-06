package com.spike.secret.template.network;

import retrofit2.Retrofit;

/**
 * API client that uses  Retrofit to do all the networking
 *
 * Created by Shyam on 2/3/17.
 */

public class RestClient {

    RestaurantsApi restaurantApi;
    ConsumerApi consumerApi;

    public RestClient(Retrofit.Builder serviceBuilder) {
        restaurantApi = serviceBuilder.build().create(RestaurantsApi.class);
        consumerApi = serviceBuilder.build().create(ConsumerApi.class);
    }

    public RestaurantsApi restaurant() {
        return restaurantApi;
    }

    public ConsumerApi consumer() {
        return consumerApi;
    }

}
