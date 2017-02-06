package com.spike.secret.template.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.spike.secret.template.R;
import com.spike.secret.template.utils.PhotoManager;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Task to get the  image
 * Responsibility:
 * - Gets the restaurant image
 * - Sets the ImageView with the retrieved {@link Bitmap}
 * - Puts the image in to the cache
 * <p>
 * Created by Shyam on 2/4/17.
 */

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = DownloadImage.class.getSimpleName();

    private final WeakReference<ImageView> imageViewReference;

    public DownloadImage(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return downloadImage(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                    PhotoManager.getInstance().addToCache("" + imageView.getTag(), bitmap);
                } else {
                    //Placeholder image
                    Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.no_image);
                    imageView.setImageDrawable(placeholder);
                }
            }
        }
    }


    private Bitmap downloadImage(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            Log.w(TAG, "Downloading " + url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();
            Log.w(TAG, "Error fetching image from " + url + ": " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}
