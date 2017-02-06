package com.spike.secret.template.utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.spike.secret.template.network.DownloadImage;

/**
 * Singleton PhotoManager class that deals with image download and caching
 * Responsibility:
 * - Gets the  image either from the cache or network
 * - Maintains the LRU cache
 * <p>
 * Created by Shyam on 2/5/17.
 */
public class PhotoManager {

    // Sets the size of the storage that's used to cache images
    private static final int IMAGE_CACHE_SIZE = 1024 * 1024 * 4;

    /*
     * Creates a cache of Bitmaps  indexed by image URLs. As new items are added to the
     * cache, the oldest items are ejected and subject to garbage collection.
     */
    private final LruCache<String, Bitmap> mPhotoCache;

    private static PhotoManager ourInstance = new PhotoManager();

    public static PhotoManager getInstance() {
        return ourInstance;
    }

    private PhotoManager() {
        mPhotoCache = new LruCache<>(IMAGE_CACHE_SIZE);
    }

    /**
     * Gets the image either from the cache or network.
     *
     * @param imageUrl    Url to fetch the image from
     * @param imageHolder {@link ImageView} holder orf the retrieved Bitmap.
     */
    public void getItemImage(String imageUrl, ImageView imageHolder) {

        if(imageUrl==null || imageHolder==null){
            return;
        }
        Bitmap cachedBitmap = mPhotoCache.get(imageUrl);
        if (cachedBitmap != null) {
            imageHolder.setImageBitmap(cachedBitmap);
        } else {
            new DownloadImage(imageHolder).execute(imageUrl);
        }
    }

    /**
     * Adds the Bitmap to the cache
     *
     * @param url  Image url
     * @param data Bitmap data.
     */
    public void addToCache(@NonNull String url, @NonNull Bitmap data) {
        mPhotoCache.put(url, data);
    }
}
