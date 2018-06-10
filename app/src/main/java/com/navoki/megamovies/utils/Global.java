package com.navoki.megamovies.utils;

import android.app.Application;
import android.content.SharedPreferences;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;


public class Global extends Application {

    private RequestQueue mRequestQueue;

    private static Global ourInstance;
    private static SharedPreferences sharedPreferences;

    public static Global getAppInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = (Global) getApplicationContext();
        sharedPreferences = getSharedPreferences(AppConstants.SHAREDPREF_NAME, MODE_PRIVATE);
    }

    private synchronized RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(ourInstance, new HurlStack());
        }
        return mRequestQueue;
    }

    public synchronized <T> void addToRequestQueue(Request<T> req) {
        req.setTag("data");
        req.setShouldCache(false);
        req.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void savePaging(String key, int page) {
        sharedPreferences.edit().putInt(key, page).apply();
    }

    public int getPaging() {
        return sharedPreferences.getInt(AppConstants.SHAREDPREF_KEY_PAGE, 1);
    }

    public void saveSortBy(String sort) {
        sharedPreferences.edit().putString(AppConstants.SHAREDPREF_KEY_SORT_URL, sort).apply();
    }

    public String getSortBy() {
        return sharedPreferences.getString(AppConstants.SHAREDPREF_KEY_SORT_URL, AppConstants.API_MOVIE_POPULAR_LIST);
    }
}
