package com.spike.secret.template.ui.restaurantlist.adapter;

import android.view.View;

/**
 * Created by Shyam on 2/5/17.
 */

public interface ListListener {

    /**
     * Called when a List item is clicked.
     *
     * @param v        View which is clicked
     * @param position the position of the item in the list
     */
    void itemClicked(View v, int position);

}
