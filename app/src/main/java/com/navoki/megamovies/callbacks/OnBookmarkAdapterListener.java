package com.navoki.megamovies.callbacks;

import android.widget.ImageView;

import com.navoki.megamovies.models.BookmarkData;

/**
 * Created by Shivam Srivastava on 6/6/2018.
 */
public interface OnBookmarkAdapterListener {
    void moveToDetailsScreen(ImageView imageView, BookmarkData bookmarkData);
}
