package com.spike.secret.template;

import android.app.Application;

import com.spike.secret.template.deps.AppComponent;
import com.spike.secret.template.deps.AppModule;
import com.spike.secret.template.deps.DaggerAppComponent;
import com.spike.secret.template.deps.NetworkModule;

/**
 * The DI container for the App.
 *
 * Created by Shyam on 2/4/17.
 */

public class DDApp extends Application {

    private AppComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
        com.spike.secret.template.utils.SSLInjector.trustAllCertificates();


    }

    public AppComponent getDDAppComponent() {
        return applicationComponent;
    }
}
