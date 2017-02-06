package com.spike.secret.template.deps;

import android.util.Log;

import com.spike.secret.template.network.RestClient;
import com.spike.secret.template.utils.PhotoManager;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shyam on 2/4/17.
 */

@Module
public class NetworkModule {


    //private static final String BASE_URL = "http://10.0.2.2:8080/";
    private static final String BASE_URL = "https://api.doordash.com/";

    @Provides
    @Singleton
    protected Interceptor providesOkHttpInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                return response;
            }
        };
    }

    @Provides
    @Singleton
    protected OkHttpClient providesOkHttpClient(Interceptor interceptor){
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    @Singleton
    protected Retrofit.Builder providesRetrofitBuilder(OkHttpClient client){
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }


    @Provides
    @Singleton
    protected PhotoManager providesPhotoManager(){
        return PhotoManager.getInstance();
    }
}
