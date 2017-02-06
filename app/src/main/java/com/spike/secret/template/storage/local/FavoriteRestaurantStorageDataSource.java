package com.spike.secret.template.storage.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.spike.secret.template.R;
import com.spike.secret.template.model.Restaurant;
import com.spike.secret.template.storage.RestaurantDataSource;
import com.spike.secret.template.storage.local.FavoriteRestaurantStorageContract.RestaurantEntry;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * RestaurantDataSource implementation with SQLite
 *
 * Created by Shyam on 2/4/17.
 */

public class FavoriteRestaurantStorageDataSource implements RestaurantDataSource {

    @NonNull
    private final Gson gson;

    @NonNull
    private final BriteDatabase dbHelper;

    @NonNull
    private Func1<Cursor, Restaurant> mapperFunction;

    private Context ctx;

    public FavoriteRestaurantStorageDataSource(@NonNull Context context) {
        ctx = context.getApplicationContext();
        FavoriteRestaurantStore dbHelper = new FavoriteRestaurantStore(ctx);
        SqlBrite sqlBrite = SqlBrite.create();
        this.dbHelper = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());
        mapperFunction = new Func1<Cursor, Restaurant>() {
            @Override
            public Restaurant call(Cursor c) {
                String data = c.getString(c.getColumnIndexOrThrow(RestaurantEntry.COLUMN_NAME_DATA));
                return gson.fromJson(data, Restaurant.class);
            }
        };
        gson = new Gson();
    }


    @Override
    public Observable<List<Restaurant>> getAllFavourite() {

        String[] projection = {
                RestaurantEntry.COLUMN_NAME_DATA
        };
        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", projection), RestaurantEntry.TABLE_NAME);
        return dbHelper.createQuery(RestaurantEntry.TABLE_NAME, sql)
                .mapToList(mapperFunction);
    }


    @Override
    public Observable<String> deleteAll() {

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    dbHelper.delete(RestaurantEntry.TABLE_NAME, null);
                    subscriber.onNext(ctx.getResources().getString(R.string.msg_cleared_tofav));
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }

            }
        });


    }

    @Override
    public Observable<Restaurant> getFavourite(@NonNull String name) {
        String[] projection = {
                RestaurantEntry.COLUMN_NAME_DATA
        };
        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?",
                TextUtils.join(",", projection), RestaurantEntry.TABLE_NAME, RestaurantEntry.COLUMN_NAME_NAME);
        return dbHelper.createQuery(RestaurantEntry.TABLE_NAME, sql, name)
                .mapToOneOrDefault(mapperFunction, null);
    }

    @Override
    public void deleteFavourite(@NonNull String name) {
        String selection = RestaurantEntry.COLUMN_NAME_NAME + " LIKE ?";
        String[] selectionArgs = {name};
        dbHelper.delete(RestaurantEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public Observable<String> addFavourite(@NonNull final Restaurant favorite) {

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    ContentValues values = new ContentValues();
                    values.put(RestaurantEntry.COLUMN_NAME_NAME, favorite.getName());
                    values.put(RestaurantEntry.COLUMN_NAME_DATA, gson.toJson(favorite));
                    dbHelper.insert(RestaurantEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_FAIL);
                    subscriber.onNext(ctx.getResources().getString(R.string.msg_added_tofav));
                    subscriber.onCompleted();
                }catch (Exception e){
                    subscriber.onError(e);
                }

            }
        });

        }

}
