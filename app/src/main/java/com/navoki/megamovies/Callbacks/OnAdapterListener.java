package com.navoki.megamovies.Callbacks;

import android.widget.ImageView;

import com.navoki.megamovies.Models.MovieData;

/**
 * Created by Shivam Srivastava on 6/6/2018.
 */
public interface OnAdapterListener {
    void getNextPagingData();
    void moveToDetailsScreen(ImageView imageView,MovieData movieData);
}
