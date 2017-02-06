package com.spike.secret.template.ui.landing;

import android.support.v4.app.FragmentManager;

import com.spike.secret.template.model.Restaurant;
import com.spike.secret.template.mvp.BasePresenter;
import com.spike.secret.template.mvp.BaseView;
import com.spike.secret.template.ui.restaurantlist.fragment.RestaurantListContract;

import java.util.List;

/**
 * Created by Shyam on 2/5/17.
 */

public interface LandingContract {

    interface View extends BaseView<LandingContract.Presenter> {
        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void configureCache(FragmentManager manager);
    }
}
