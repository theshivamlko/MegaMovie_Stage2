package com.navoki.megamovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.navoki.megamovies.models.MovieData;

import java.util.List;

/**
 * Created by Shivam Srivastava on 6/8/2018.
 */
@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies  where sortby=:sortby")
    List<MovieData> getAll(String sortby);

    @Query("SELECT * FROM movies where  sortby=:sortby")
    LiveData<List<MovieData>> getMovieTaskList(String sortby);

    @Query("SELECT * FROM movies where isfavorite=1")
    LiveData<List<MovieData>> getFavoriteList();

    @Query("SELECT * FROM movies where id = :id")
    LiveData<MovieData> getMovie(String id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieData movieData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MovieData> movieDataList);

    @Query("DELETE FROM movies where sortby=:sortby")
    void deleteAll(String sortby);
}
