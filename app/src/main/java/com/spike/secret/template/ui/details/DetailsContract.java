package com.spike.secret.template.ui.details;

import com.spike.secret.template.model.Restaurant;
import com.spike.secret.template.mvp.BasePresenter;
import com.spike.secret.template.mvp.BaseView;

import java.util.List;

/**
 * Created by Shyam on 2/5/17.
 */

public class DetailsContract {

    interface View extends BaseView<Presenter> {
        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void addToFav(Restaurant favRestaurant);
    }
}
