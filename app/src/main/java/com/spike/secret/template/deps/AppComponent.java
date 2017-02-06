package com.spike.secret.template.deps;

import com.spike.secret.template.ui.details.DetailsPresenter;
import com.spike.secret.template.ui.landing.LandingPresenter;
import com.spike.secret.template.ui.profile.ProfilePresenter;
import com.spike.secret.template.ui.restaurantlist.fragment.RestaurantListPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Shyam on 2/4/17.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    //Activities

    // Presenters
    void inject(RestaurantListPresenter presenter);

    void inject(DetailsPresenter presenter);

    void inject(LandingPresenter presenter);

    void inject(ProfilePresenter presenter);


}
