package com.navoki.megamovies.callbacks;

import android.widget.ImageView;

import com.navoki.megamovies.models.MovieData;

/**
 * Created by Shivam Srivastava on 6/6/2018.
 */
public interface OnAdapterListener {
    void getNextPagingData();
    void moveToDetailsScreen(ImageView imageView,MovieData movieData);
}
