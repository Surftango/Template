package com.spike.secret.template.ui.details;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.spike.secret.template.R;
import com.spike.secret.template.model.Restaurant;
import com.spike.secret.template.model.Restaurants;
import com.spike.secret.template.ui.details.fragment.DetailsFragment;
import com.spike.secret.template.ui.restaurantlist.fragment.RestaurantListFragment;

import java.util.List;


/**
 * This screen displays the details of the restaurant selected from the restaurant list screen.
 * Also provide the ability to add a restaurant to a favourites list
 *
 * Created by Shyam on 2/5/17.
 */

public class DetailsActivity extends AppCompatActivity implements DetailsContract.View {

    public static final String BUNDLE_RESTAURANT = "restaurant";
    public static final String BUNDLE_RESTAURANT_POS = "position";

    private static String TAG = DetailsActivity.class.getSimpleName();
    /**
     * The number of product detail pages to show.
     */
    private static int NUM_PAGES = 0;
    /**
     * Handle to Products that is passed across from the Product List screen.
     */
    List<Restaurant> restaurantList;
    /**
     * View for hooking up SnackBar
     */
    View rootView;
    private Handler uiHook;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    @NonNull
    private DetailsContract.Presenter presenter;

    private boolean showFav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHook = new Handler();
        int position = 0;
        try {
            restaurantList = ((Restaurants) getIntent().getExtras().getSerializable(BUNDLE_RESTAURANT)).getRestaurants();
            NUM_PAGES = restaurantList.size();
            position = (int) getIntent().getExtras().getSerializable(BUNDLE_RESTAURANT_POS);
            showFav = getIntent().getExtras().getBoolean(RestaurantListFragment.sCONTENT_CHOICE);
        } catch (Exception e) {
            Log.e(TAG, "Error getting JSON data " + e);
            restaurantList = null;
        }
        setContentView(R.layout.activity_detail_holder);
        rootView = findViewById(android.R.id.content);
        mPager = (ViewPager) findViewById(R.id.pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.restaurant_details));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (restaurantList == null) {
            mPager.setVisibility(View.GONE);
            findViewById(R.id.empty_message).setVisibility(View.VISIBLE);
        } else {

            mPager = (ViewPager) findViewById(R.id.pager);
            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(mPagerAdapter);
            mPager.setCurrentItem(position);
        }
        setPresenter(new DetailsPresenter(this, this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        if (showFav) {
            menu.removeItem(R.id.action_add_fav);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_cart:
                Toast.makeText(DetailsActivity.this, getString(R.string.err_not_implemented), Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_add_fav:
                presenter.addToFav(restaurantList.get(mPager.getCurrentItem()));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMessage(final String message) {
        uiHook.post(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setPresenter(@NonNull DetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    /**
     * A simple pager adapter that represents  ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            DetailsFragment detailsFrag = new DetailsFragment();
            Bundle productBundle = new Bundle();
            productBundle.putSerializable(DetailsFragment.BUNDLE_RESTAURANT, restaurantList.get(position));
            detailsFrag.setArguments(productBundle);
            return detailsFrag;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
