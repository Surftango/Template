package com.spike.secret.template.storage.local;

import android.provider.BaseColumns;

/**
 * Created by Shyam on 2/4/17.
 */

public final class FavoriteRestaurantStorageContract {

    private FavoriteRestaurantStorageContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class RestaurantEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DATA = "data";
    }
}
