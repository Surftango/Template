package com.spike.secret.template.ui.restaurantlist.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.spike.secret.template.R;
import com.spike.secret.template.model.Restaurant;
import com.spike.secret.template.model.Restaurants;
import com.spike.secret.template.ui.details.DetailsActivity;
import com.spike.secret.template.ui.restaurantlist.adapter.ItemListAdapter;
import com.spike.secret.template.ui.restaurantlist.adapter.ListListener;
import com.spike.secret.template.ui.restaurantlist.custom.CustomGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shyam on 2/5/17.
 */

public class RestaurantListFragment extends Fragment implements RestaurantListContract.View, ListListener {

    public static final String sCONTENT_CHOICE = "content_choice";
    private static final String sVIEW_CHOICE = "view_layout";
    private static String TAG = RestaurantListFragment.class.getSimpleName();
    @NonNull
    private RestaurantListContract.Presenter presenter;
    private Button mRefresh;
    private SwipeRefreshLayout mSwipeContainer;
    private RecyclerView mRestaurantListView;
    private ViewGroup mEmptyListView, mErrorView;
    private int mGridItemWidth;
    // RecycleView related controls
    private ItemListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    // View Layout toggle
    private RestaurantListFragment.ViewLayout mViewLayout;
    private List<Restaurant> retrievedRestaurants = new ArrayList<Restaurant>();
    private boolean showFavourites;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewLayout = setDefaultView();
        if (savedInstanceState != null) {
            mViewLayout = savedInstanceState.getInt(sVIEW_CHOICE) == RestaurantListFragment.ViewLayout.LIST.getValue() ? RestaurantListFragment.ViewLayout.LIST : RestaurantListFragment.ViewLayout.GRID;
        }
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_restaurant_list, container, false);
        setPresenter(new RestaurantListPresenter(this, this.getContext()));
        showFavourites = getArguments().getBoolean(sCONTENT_CHOICE);
        initView(rootView);
        getContent();
        return rootView;
    }

    private void initView(ViewGroup rootView) {
        try {
            setViewLayout(mViewLayout);
            mRefresh = (Button) rootView.findViewById(R.id.refresh);
            mRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getRestaurants(true);

                }
            });
            mSwipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
            mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getContent();
                }
            });
            mRestaurantListView = (RecyclerView) rootView.findViewById(R.id.item_list_view);
            mEmptyListView = (ViewGroup) rootView.findViewById(R.id.empty_list);
            mErrorView = (ViewGroup) rootView.findViewById(R.id.error_view);

            mRestaurantListView.setHasFixedSize(true);
            mRestaurantListView.setLayoutManager(mLayoutManager);
            mAdapter = new ItemListAdapter(retrievedRestaurants, this, this.getContext());
            mRestaurantListView.setAdapter(mAdapter);
            mSwipeContainer.setRefreshing(true);
            registerForContextMenu(rootView);
            validateView(false);
        } catch (Exception e) {
            Log.e(TAG, "Error preparing View " + e);
            showError(e.getMessage());
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_restaurant_list, menu);
        try {
            switch (mViewLayout.getValue()) {
                case 0:
                    menu.getItem(1).setIcon(R.drawable.grid_toggle_white);
                    break;
                case 1:
                    menu.getItem(1).setIcon(R.drawable.list_toggle_white);
                    break;
            }
        } catch (Exception ignore) {
            //Suppress errors with setting menu item
            Log.w(TAG, "Error in setting menu item icon " + ignore);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        /*if (id == R.id.action_refresh) {
            getContent();
            return true;
        } else*/ if (id == R.id.action_view) {
            if (mLayoutManager instanceof GridLayoutManager) {
                mLayoutManager = new LinearLayoutManager(this.getContext());
                mViewLayout = RestaurantListFragment.ViewLayout.LIST;
                item.setIcon(R.drawable.grid_toggle_white);
            } else {
                mLayoutManager = new CustomGridLayoutManager(this.getContext(), mGridItemWidth);
                mViewLayout = RestaurantListFragment.ViewLayout.GRID;
                item.setIcon(R.drawable.list_toggle_white);
            }
            mRestaurantListView.setLayoutManager(mLayoutManager);
            mAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * Display the view based on the application state
     */
    private void validateView(final boolean isError) {

        mSwipeContainer.setRefreshing(false);
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(isError){
                    mErrorView.setVisibility(View.VISIBLE);
                    mEmptyListView.setVisibility(View.GONE);
                    mRestaurantListView.setVisibility(View.GONE);
                }else{
                    if (retrievedRestaurants.size() == 0) {
                        mEmptyListView.setVisibility(View.VISIBLE);
                        mRestaurantListView.setVisibility(View.GONE);
                    } else {
                        mEmptyListView.setVisibility(View.GONE);
                        mRestaurantListView.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
    }

    /**
     *Sets the view layout based on the device screen size
     * @return
     */
    private RestaurantListFragment.ViewLayout setDefaultView() {

        return ((getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE) ? RestaurantListFragment.ViewLayout.GRID : RestaurantListFragment.ViewLayout.LIST;
    }

    /*
     * Sets the view based on chosen view
     * @param viewLayout
     */
    private void setViewLayout(RestaurantListFragment.ViewLayout viewLayout) {

        mGridItemWidth = getResources().getDisplayMetrics().densityDpi;
        switch (viewLayout.getValue()) {
            case 0:
                mLayoutManager = new LinearLayoutManager(this.getContext());
                break;
            case 1:
                mLayoutManager = new CustomGridLayoutManager(this.getContext(), mGridItemWidth);
                break;
        }
    }

    @Override
    public void setPresenter(@NonNull RestaurantListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void updateUi(List<Restaurant> restaurants) {
        retrievedRestaurants.clear();
        retrievedRestaurants.addAll(restaurants);
        mAdapter.notifyDataSetChanged();
        validateView(false);
    }

    @Override
    public void showError(String message) {
        validateView(true);
        //Snackbar.make(mRestaurantListView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void itemClicked(View v, int position) {
        Intent productDetailsIntent = new Intent(this.getContext(), DetailsActivity.class);
        productDetailsIntent.putExtra(DetailsActivity.BUNDLE_RESTAURANT, new Restaurants(retrievedRestaurants));
        productDetailsIntent.putExtra(DetailsActivity.BUNDLE_RESTAURANT_POS, position);
        productDetailsIntent.putExtra(sCONTENT_CHOICE, showFavourites);
        startActivity(productDetailsIntent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        if (showFavourites) {
            getActivity().getMenuInflater().inflate(R.menu.fav_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_delete_all:
                presenter.deleteAllFav();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //persists the view choice
        outState.putInt(sVIEW_CHOICE, mViewLayout.getValue());
        super.onSaveInstanceState(outState);
    }

    /**
     * Get content based on the application state
     */
    private void getContent(){
        if (showFavourites) {
            presenter.getFavRestaurants();
        } else {
            presenter.getRestaurants(false);
        }
    }

    //view toggle options
    private enum ViewLayout {
        LIST(0),
        GRID(1);
        int value;

        ViewLayout(int i) {
            value = i;
        }

        public int getValue() {
            return value;
        }
    }

}
