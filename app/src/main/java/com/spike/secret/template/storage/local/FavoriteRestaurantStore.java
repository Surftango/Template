package com.spike.secret.template.storage.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shyam on 2/4/17.
 */

public class FavoriteRestaurantStore extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "restaurant.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FavoriteRestaurantStorageContract.RestaurantEntry.TABLE_NAME + " (" +
                    FavoriteRestaurantStorageContract.RestaurantEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    FavoriteRestaurantStorageContract.RestaurantEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    FavoriteRestaurantStorageContract.RestaurantEntry.COLUMN_NAME_DATA + TEXT_TYPE +
                    " )";

    public FavoriteRestaurantStore(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //N/A
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //N/A
    }

}
