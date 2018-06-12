package com.navoki.megamovies.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.navoki.megamovies.models.CastModel;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Shivam Srivastava on 6/8/2018.
 */
public class CastsConvertor {

    @TypeConverter
    public static String encodeGenre(List<CastModel> genreModelList) {

        Gson gson = new Gson();
        return gson.toJson(genreModelList);
    }

    @TypeConverter
    public static List<CastModel> decodeGenre(String value) {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<CastModel>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}
