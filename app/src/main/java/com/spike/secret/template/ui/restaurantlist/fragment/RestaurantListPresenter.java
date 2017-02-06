package com.spike.secret.template.ui.restaurantlist.fragment;

import android.content.Context;

import com.spike.secret.template.DDApp;
import com.spike.secret.template.deps.AppComponent;
import com.spike.secret.template.model.Restaurant;
import com.spike.secret.template.mvp.BaseView;
import com.spike.secret.template.mvp.RootPresenter;
import com.spike.secret.template.network.RestClient;
import com.spike.secret.template.storage.RestaurantDataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Shyam on 2/5/17.
 */

public class RestaurantListPresenter extends RootPresenter implements RestaurantListContract.Presenter {

    @Inject
    RestClient apiClient;

    @Inject
    ApiCacheProvider apiCache;

    @Inject
    RestaurantDataSource dataStore;

    public RestaurantListPresenter(BaseView view, Context ctx) {
        attachView(view);
        AppComponent component = ((DDApp) ctx.getApplicationContext()).getDDAppComponent();
        component.inject(this);
    }

    @Override
    public void getRestaurants(boolean force) {
        if (force || apiCache.getRequest() == null) {
            apiCache.setRequest(apiClient.restaurant().getRestaurants(37.287750, -121.862569).cache());
        }
        Subscription sub = apiCache.getRequest()
                .compose(this.<List<Restaurant>>applySchedulers())
                .subscribe(new Subscriber<List<Restaurant>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            ((RestaurantListContract.View) getView()).showError(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(List<Restaurant> restaurants) {
                        if (getView() != null) {
                            ((RestaurantListContract.View) getView()).updateUi(restaurants);
                        }
                    }
                });
        compositeSubscription.add(sub);

    }

    @Override
    public void getFavRestaurants() {
        dataStore.getAllFavourite()
                .compose(this.<List<Restaurant>>applySchedulers())
                .subscribe(new Action1<List<Restaurant>>() {
                    @Override
                    public void call(List<Restaurant> restaurants) {
                        if (getView() != null) {
                            ((RestaurantListContract.View) getView()).updateUi(restaurants);
                        }
                    }
                });
    }

    @Override
    public void deleteAllFav() {
        dataStore.deleteAll()
                .compose(this.<String>applySchedulers())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String restaurants) {
                        if (getView() != null) {
                            ((RestaurantListContract.View) getView()).updateUi(new ArrayList<Restaurant>());
                        }
                    }
                });
    }

    @Override
    public void searchFavorites(String name) {

        //((RestaurantListContract.View)getView()).updateUi();
    }


}
