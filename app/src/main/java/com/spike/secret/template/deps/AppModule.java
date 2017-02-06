package com.spike.secret.template.deps;

import android.support.v4.app.FragmentManager;

import com.spike.secret.template.DDApp;
import com.spike.secret.template.network.RestClient;
import com.spike.secret.template.storage.RestaurantDataSource;
import com.spike.secret.template.storage.local.FavoriteRestaurantStorageDataSource;
import com.spike.secret.template.ui.restaurantlist.fragment.ApiCacheProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Provider for all Application stuffs
 *
 * Created by Shyam on 2/4/17.
 */

@Module
public class AppModule {

    private final DDApp doorDashApp;

    public AppModule(DDApp doorDashApp) {
        this.doorDashApp = doorDashApp;
    }

    @Provides
    @Singleton
    protected RestClient providesRestClient(Retrofit.Builder builder){
        return new RestClient(builder);
    }

    @Provides
    @Singleton
    protected RestaurantDataSource providesRestaurantDataSource(){
        return new FavoriteRestaurantStorageDataSource(doorDashApp.getApplicationContext());
    }

    @Provides
    @Singleton
    protected ApiCacheProvider provideApiCache(){
        return new ApiCacheProvider();
    }

}
