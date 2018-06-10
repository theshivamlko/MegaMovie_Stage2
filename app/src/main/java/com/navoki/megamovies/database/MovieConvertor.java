package com.navoki.megamovies.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.navoki.megamovies.models.MovieData;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Shivam Srivastava on 6/8/2018.
 */
public class MovieConvertor {

    @TypeConverter
    public static String encodeMovie(List<MovieData> movieDataList) {

        Gson gson = new Gson();
        return gson.toJson(movieDataList);
    }

    @TypeConverter
    public static List<MovieData> decodeMovie(String value) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<MovieData>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}
