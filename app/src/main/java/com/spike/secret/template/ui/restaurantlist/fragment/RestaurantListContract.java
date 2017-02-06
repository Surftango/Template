package com.spike.secret.template.ui.restaurantlist.fragment;

import android.support.v4.app.FragmentManager;

import com.spike.secret.template.model.Restaurant;
import com.spike.secret.template.mvp.BasePresenter;
import com.spike.secret.template.mvp.BaseView;

import java.util.List;

/**
 * Created by Shyam on 2/5/17.
 */

public class RestaurantListContract {

    interface View extends BaseView<Presenter> {
        void updateUi(List<Restaurant> restaurants);

        void showError(String message);
    }

    interface Presenter extends BasePresenter {
        void getRestaurants(boolean force);

        void getFavRestaurants();

        void deleteAllFav();

        void searchFavorites(String name);
    }
}
