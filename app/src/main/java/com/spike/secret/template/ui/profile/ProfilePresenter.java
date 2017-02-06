package com.spike.secret.template.ui.profile;

import android.content.Context;
import android.support.annotation.NonNull;

import com.spike.secret.template.DDApp;
import com.spike.secret.template.R;
import com.spike.secret.template.model.Consumer;
import com.spike.secret.template.model.LoginRequest;
import com.spike.secret.template.model.LoginResponse;
import com.spike.secret.template.model.Restaurant;
import com.spike.secret.template.mvp.BaseView;
import com.spike.secret.template.mvp.RootPresenter;
import com.spike.secret.template.network.RestClient;
import com.spike.secret.template.storage.RestaurantDataSource;

import javax.inject.Inject;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Shyam on 2/5/17.
 */

public class ProfilePresenter extends RootPresenter implements ProfileContract.Presenter {

    @Inject
    RestClient apiClient;

    private Context ctx;

    private String tokenHolder;

    public ProfilePresenter(BaseView view, Context ctx) {
        this.ctx = ctx.getApplicationContext();
        attachView(view);
        ((DDApp) ctx.getApplicationContext()).getDDAppComponent().inject(this);
    }

    @Override
    public void authenticateAndRetrieve(String email, String password) {
        apiClient.consumer().getAuthToken(new LoginRequest(email, password))
                .compose(this.<LoginResponse>applySchedulers())
                .subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            ((ProfileContract.View) getView()).showMessage(ctx.getResources().getString(R.string.err_login));
                        }
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        tokenHolder = loginResponse.getToken();
                        getConsumerProfile();
                    }
                });
    }

    @Override
    public void getConsumerProfile() {
        apiClient.consumer().getConsumerProfile("JWT "+tokenHolder)
                .compose(this.<Consumer>applySchedulers())
                .subscribe(new Subscriber<Consumer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(getView()!=null){
                            ((ProfileContract.View)getView()).showMessage(ctx.getResources().getString(R.string.err_get_profile));
                        }
                    }

                    @Override
                    public void onNext(Consumer consumer) {
                        if(getView()!=null){
                            ((ProfileContract.View)getView()).updateView(consumer);
                        }
                    }
                });
    }

    @Override
    public String token() {
        return tokenHolder;
    }


}
