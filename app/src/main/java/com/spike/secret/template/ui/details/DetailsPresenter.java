package com.spike.secret.template.ui.details;

import android.content.Context;

import com.spike.secret.template.DDApp;
import com.spike.secret.template.model.Restaurant;
import com.spike.secret.template.mvp.BaseView;
import com.spike.secret.template.mvp.RootPresenter;
import com.spike.secret.template.storage.RestaurantDataSource;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Shyam on 2/5/17.
 */

public class DetailsPresenter extends RootPresenter implements DetailsContract.Presenter {

    @Inject
    RestaurantDataSource dataStore;

    private Context ctx;

    public DetailsPresenter(BaseView view, Context ctx) {
        this.ctx = ctx.getApplicationContext();
        attachView(view);
        ((DDApp) ctx.getApplicationContext()).getDDAppComponent().inject(this);
    }

    @Override
    public void addToFav(Restaurant favRestaurant) {
        try {
            dataStore.addFavourite(favRestaurant)
                    .doOnError(new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            if (getView() != null) {
                                ((DetailsContract.View) getView()).showMessage(throwable.getMessage());
                            }
                        }
                    })
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            if (getView() != null) {
                                ((DetailsContract.View) getView()).showMessage(s);
                            }

                        }
                    });

        } catch (Exception e) {
            if (getView() != null) {
                ((DetailsContract.View) getView()).showMessage(e.getMessage());
            }
        }
    }


}
