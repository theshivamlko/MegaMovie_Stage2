package com.navoki.megamovies.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.navoki.megamovies.Models.MovieData;

import java.util.List;

/**
 * Created by Shivam Srivastava on 6/8/2018.
 */
@Dao
public interface MovieDao {
    @Query("SELECT * FROM favorites")
    LiveData<List<MovieData>> getMovieTaskList();

    @Query("SELECT * FROM favorites where id = :id")
    MovieData getBookmarkedMovie(String id);

    @Insert
    void insertMovie(MovieData movieData);

    @Query("DELETE FROM favorites where id = :id")
    void deleteMovie(String id);
}
