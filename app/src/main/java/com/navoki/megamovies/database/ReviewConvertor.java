package com.navoki.megamovies.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.navoki.megamovies.models.CastModel;
import com.navoki.megamovies.models.ReviewModel;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Shivam Srivastava on 6/8/2018.
 */
public class ReviewConvertor {

    @TypeConverter
    public static String encodeGenre(List<ReviewModel> reviewModelList) {

        Gson gson = new Gson();
        return gson.toJson(reviewModelList);
    }

    @TypeConverter
    public static List<ReviewModel> decodeGenre(String value) {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<ReviewModel>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}
