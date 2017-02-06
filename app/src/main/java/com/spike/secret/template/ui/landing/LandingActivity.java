package com.spike.secret.template.ui.landing;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.spike.secret.template.R;
import com.spike.secret.template.ui.profile.UserProfileFragment;
import com.spike.secret.template.ui.restaurantlist.fragment.RestaurantListFragment;

/**
 * Created by Shyam on 2/5/17.
 */

public class LandingActivity extends AppCompatActivity implements LandingContract.View, NavigationView.OnNavigationItemSelectedListener {

    private static String TAG = LandingActivity.class.getSimpleName();
    private static String VIEW_CHOICE = "VIEW_STATE";
    /**
     * View for hooking up SnackBar
     */
    View rootView;
    @NonNull
    private LandingContract.Presenter presenter;
    private Handler mHandler;
    private int currentView  = R.id.nav_restaurants;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_wrapper);
        rootView = findViewById(android.R.id.content);
        setPresenter(new LandingPresenter(this, this));
        if(savedInstanceState!=null){
            currentView = savedInstanceState.getInt(VIEW_CHOICE)==0?R.id.nav_restaurants:savedInstanceState.getInt(VIEW_CHOICE);
        }
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            mHandler = new Handler();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            presenter.configureCache(getSupportFragmentManager());
            displayView(currentView);
        } catch (Exception e) {
            Log.e(TAG, "Error preparing View " + e);
            showMessage(e.getMessage());
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displayView(item.getItemId());
        return true;
    }

    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);
        currentView = viewId;
        switch (viewId) {
            case R.id.nav_restaurants: {
                Bundle args = new Bundle();
                args.putBoolean(RestaurantListFragment.sCONTENT_CHOICE, false);
                fragment = new RestaurantListFragment();
                fragment.setArguments(args);
                title = getResources().getString(R.string.restaurants);
            }
            break;
            case R.id.nav_fav: {
                Bundle args = new Bundle();
                args.putBoolean(RestaurantListFragment.sCONTENT_CHOICE, true);
                fragment = new RestaurantListFragment();
                fragment.setArguments(args);
                title = getResources().getString(R.string.fav);
            }
            break;

            case R.id.nav_profile:
                fragment = new UserProfileFragment();
                title = getResources().getString(R.string.action_profile);
                break;

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(@NonNull LandingContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onSaveInstanceState (Bundle outState){
        outState.putInt(VIEW_CHOICE,currentView);
        super.onSaveInstanceState(outState);
    }
}
