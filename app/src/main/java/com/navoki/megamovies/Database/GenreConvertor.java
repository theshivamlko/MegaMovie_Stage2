package com.navoki.megamovies.Database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.navoki.megamovies.Models.GenreModel;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Shivam Srivastava on 6/8/2018.
 */
public class GenreConvertor {

    @TypeConverter
    public static String encodeGenre(List<GenreModel> genreModelList) {

        Gson gson = new Gson();
        return gson.toJson(genreModelList);
    }

    @TypeConverter
    public static List<GenreModel> decodeGenre(String value) {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<GenreModel>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}
