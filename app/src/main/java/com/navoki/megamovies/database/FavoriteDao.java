package com.navoki.megamovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.navoki.megamovies.models.FavoriteData;
import com.navoki.megamovies.models.MovieData;

import java.util.List;

/**
 * Created by Shivam Srivastava on 6/8/2018.
 */
@Dao
public interface FavoriteDao {
  /*  @Query("SELECT * FROM favorite")
    LiveData<List<MovieData>> getFavList();
  */
    @Query("SELECT * FROM favorite where id=:id")
    FavoriteData checkFav(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteData favoriteData);

    @Query("DELETE FROM movies where id=:id")
    void delete(String id);
}
