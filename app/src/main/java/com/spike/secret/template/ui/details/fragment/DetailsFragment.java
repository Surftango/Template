package com.spike.secret.template.ui.details.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spike.secret.template.R;
import com.spike.secret.template.model.Restaurant;
import com.spike.secret.template.utils.PhotoManager;

/**
 * Created by Shyam on 2/5/17.
 */

public class DetailsFragment extends Fragment {

    public static final String BUNDLE_RESTAURANT = "restaurant";

    @SuppressWarnings("unused")
    private static String TAG = DetailsFragment.class.getSimpleName();
    Restaurant restaurant;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        restaurant = (Restaurant) args.getSerializable(BUNDLE_RESTAURANT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_restaurant_details, container, false);

        try {
            ((TextView) rootView.findViewById(R.id.productName)).setText(""+restaurant.getName());
            ((TextView) rootView.findViewById(R.id.shortDescription)).setText(""+restaurant.getAddress().getPrintable_address());
            ((TextView) rootView.findViewById(R.id.open_status)).setText(""+restaurant.getStatus_type());
            ((TextView) rootView.findViewById(R.id.price)).setText(""+restaurant.getPrice_range());
            ((TextView) rootView.findViewById(R.id.longDescription)).setText(""+restaurant.getDescription());
            updateRatingStars(rootView, restaurant.getRating());
        }catch (Exception e){
            Log.e(TAG,"Error in populating details"+e);
        }
        try{
            PhotoManager.getInstance().getItemImage(restaurant.getCoverImage(), (ImageView) rootView.findViewById(R.id.productImage));
        }catch (Exception e){
            Log.e(TAG,"Error in populating details"+e);
        }
        return rootView;
    }

    /*
     * Updates the rating 'stars'
     * @param rootView
     * @param rating
     */
    private void updateRatingStars(ViewGroup rootView, Double rating) {
        LinearLayout parent = ((LinearLayout) rootView.findViewById(R.id.reviewRating));
        for (int i = 0; i < rating.intValue(); i++) {
            ((ImageView) parent.getChildAt(i)).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.full_star));
        }
        if (rating % 1 != 0) {
            ((ImageView) parent.getChildAt(rating.intValue())).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.half_star));
        }

    }
}
