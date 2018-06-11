package com.navoki.megamovies.utils;

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

    /*Constants*/
    public static final int TRUE = 1;
    public static final int FALSE = 0;


    /* SharedPreference*/
    public static final String SHAREDPREF_NAME = "appData";
    public static final String SHAREDPREF_KEY_SORT_URL = "sortBy";
    public static final String SHAREDPREF_KEY_PAGE = "page";
    public static final String SHAREDPREF_VALUE_POPULAR = "popular";
    public static final String SHAREDPREF_VALUE_RATING = "rating";

//Avengers: Infinity War 0 299536
    //Black Panther 0 284054
}
