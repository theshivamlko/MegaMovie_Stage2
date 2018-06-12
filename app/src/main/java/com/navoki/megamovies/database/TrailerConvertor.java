package com.navoki.megamovies.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Shivam Srivastava on 6/8/2018.
 */
public class TrailerConvertor {

    @TypeConverter
    public static String encodeGenre(List<String> list) {

        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<String> decodeGenre(String value) {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}
