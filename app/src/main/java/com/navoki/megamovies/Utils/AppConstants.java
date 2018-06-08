package com.navoki.megamovies.Utils;

import com.navoki.megamovies.BuildConfig;

/**
 * Created by Shivam Srivastava on 6/5/2018.
 */
public class AppConstants {
    // API urls
    public static final String API_MOVIE_POPULAR_LIST = BuildConfig.BASE_URL + "movie/popular";
    public static final String API_MOVIE_HIGH_RATE_LIST = BuildConfig.BASE_URL + "movie/top_rated";
    public static final String API_MOVIE_DETAILS = BuildConfig.BASE_URL + "movie";

    // Intent keys
    public static final String EXTRA_MOVIE_DATA = "movieData";
    public static final String EXTRA_VIDEO_LIST = "videoList";
    public static final String EXTRA_IS_BOOKMARKED = "isBookMarked";
}