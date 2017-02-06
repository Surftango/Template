package com.spike.secret.template.ui.landing;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.spike.secret.template.DDApp;
import com.spike.secret.template.mvp.BaseView;
import com.spike.secret.template.mvp.RootPresenter;
import com.spike.secret.template.ui.restaurantlist.fragment.ApiCacheProvider;

import javax.inject.Inject;

/**
 * Created by Shyam on 2/5/17.
 */

public class LandingPresenter extends RootPresenter implements LandingContract.Presenter {

    @Inject
    ApiCacheProvider apiCache;

    public LandingPresenter(BaseView view, Context ctx) {
        attachView(view);
        ((DDApp) ctx.getApplicationContext()).getDDAppComponent().inject(this);
    }

    @Override
    public void configureCache(FragmentManager fm) {
        if (fm.findFragmentByTag(ApiCacheProvider.RETAIN_TAG) == null) {
            fm.beginTransaction().add(apiCache, ApiCacheProvider.RETAIN_TAG).commit();
        }
    }
}
