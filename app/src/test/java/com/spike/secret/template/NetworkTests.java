package com.spike.secret.template;

import com.spike.secret.template.deps.AppComponent;
import com.spike.secret.template.deps.DaggerAppComponent;
import com.spike.secret.template.deps.NetworkModule;
import com.spike.secret.template.model.Restaurant;
import com.spike.secret.template.network.RestClient;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.functions.Action1;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NetworkTests {

    private static String valid_response1="[{\n" +
            "    \"is_time_surging\": false,\n" +
            "    \"max_order_size\": null,\n" +
            "    \"delivery_fee\": 0,\n" +
            "    \"max_composite_score\": 10,\n" +
            "    \"id\": 7402,\n" +
            "    \"menus\": [\n" +
            "      {\n" +
            "        \"is_catering\": false,\n" +
            "        \"subtitle\": \"\",\n" +
            "        \"id\": 59220,\n" +
            "        \"name\": \"#1 Chinese Kitchen (Lunch)\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"is_catering\": false,\n" +
            "        \"subtitle\": \"\",\n" +
            "        \"id\": 11301,\n" +
            "        \"name\": \"#1 Chinese Kitchen\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"composite_score\": 8,\n" +
            "    \"status_type\": \"open\",\n" +
            "    \"is_only_catering\": false,\n" +
            "    \"status\": \"49 mins\",\n" +
            "    \"asap_time\": 49,\n" +
            "    \"description\": \"Chinese\",\n" +
            "    \"business\": {\n" +
            "      \"id\": 6073,\n" +
            "      \"name\": \"#1 Chinese Kitchen\"\n" +
            "    },\n" +
            "    \"tags\": [\n" +
            "      \"Chinese\",\n" +
            "      \"Catering\"\n" +
            "    ],\n" +
            "    \"yelp_review_count\": 211,\n" +
            "    \"business_id\": 6073,\n" +
            "    \"extra_sos_delivery_fee\": 0,\n" +
            "    \"yelp_rating\": 4,\n" +
            "    \"cover_img_url\": \"https://cdn.doordash.com/media/restaurant/cover/1-Chinese-Kitchen.png\",\n" +
            "    \"header_img_url\": \"https://doordash-static.s3.amazonaws.com/media/photos/aacc63c4-cedf-4756-8b72-70d41e1a48da-retina-large.jpg\",\n" +
            "    \"address\": {\n" +
            "      \"city\": \"San Jose\",\n" +
            "      \"state\": \"CA\",\n" +
            "      \"street\": \"5585 Snell Avenue\",\n" +
            "      \"lat\": 37.2522157,\n" +
            "      \"lng\": -121.831435,\n" +
            "      \"printable_address\": \"5585 Snell Avenue, San Jose, CA 95123, USA\"\n" +
            "    },\n" +
            "    \"price_range\": 2,\n" +
            "    \"slug\": \"-1-chinese-kitchen-san-jose\",\n" +
            "    \"name\": \"#1 Chinese Kitchen (San Jose)\",\n" +
            "    \"url\": \"/store/-1-chinese-kitchen-san-jose-7402/\",\n" +
            "    \"service_rate\": 7,\n" +
            "    \"promotion\": null,\n" +
            "    \"featured_category_description\": null\n" +
            "  }]";


    @Test
    public void testInvalidResponse() throws IOException {

        MockWebServer mockWebServer = new MockWebServer();
        RestClient client = mockRestClient(mockWebServer);
        mockWebServer.enqueue(new MockResponse().setBody("your json body"));
        client.restaurant().getRestaurants(12,121)
                .subscribe(new Subscriber<List<Restaurant>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        assertTrue(e!=null);
                    }

                    @Override
                    public void onNext(List<Restaurant> restaurants) {
                        fail();
                    }
                });

        //Finish web server
        mockWebServer.shutdown();
     }


    @Test
    public void testValidResponse() throws IOException {

        MockWebServer mockWebServer = new MockWebServer();
        RestClient client = mockRestClient(mockWebServer);
        mockWebServer.enqueue(new MockResponse().setBody(valid_response1));
        client.restaurant().getRestaurants(12,121)
                .subscribe(new Subscriber<List<Restaurant>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        fail();

                    }

                    @Override
                    public void onNext(List<Restaurant> restaurants) {
                        assertTrue(restaurants!=null);
                    }
                });

        //Finish web server
        mockWebServer.shutdown();
    }

    public static RestClient mockRestClient(MockWebServer mockWebServer) {
        Retrofit.Builder retrofitBuilder =  new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return new RestClient(retrofitBuilder);
    }
}