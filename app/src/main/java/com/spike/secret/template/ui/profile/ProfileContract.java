package com.spike.secret.template.ui.profile;

import com.spike.secret.template.model.Consumer;
import com.spike.secret.template.model.LoginRequest;
import com.spike.secret.template.model.Restaurant;
import com.spike.secret.template.mvp.BasePresenter;
import com.spike.secret.template.mvp.BaseView;

/**
 * Created by Shyam on 2/5/17.
 */

public class ProfileContract {

    interface View extends BaseView<Presenter> {
        void updateView(Consumer consumer);
        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {
        void authenticateAndRetrieve(String email, String password);
        void getConsumerProfile();
        String token();
    }
}
