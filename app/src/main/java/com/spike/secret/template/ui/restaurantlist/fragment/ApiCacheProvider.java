package com.spike.secret.template.ui.restaurantlist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.spike.secret.template.model.Restaurant;

import java.util.List;

import rx.Observable;

/**
 * Created by Shyam on 2/5/17.
 */

public class ApiCacheProvider extends Fragment {

    public static String RETAIN_TAG = "config_fixer";

    Observable<List<Restaurant>> request;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Observable<List<Restaurant>> getRequest() {
        return request;
    }

    public void setRequest(Observable<List<Restaurant>> request) {
        this.request = request;
    }
}
