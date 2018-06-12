package com.navoki.megamovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.navoki.megamovies.models.BookmarkData;

import java.util.List;

/**
 * Created by Shivam Srivastava on 6/8/2018.
 */
@Dao
public interface BookmarkDao {
    @Query("SELECT * FROM bookmarks")
    LiveData<List<BookmarkData>> getFavList();

    @Query("SELECT * FROM bookmarks where id=:id")
    BookmarkData checkFav(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BookmarkData bookmarkData);

    @Query("DELETE FROM bookmarks where id=:id")
    void delete(String id);
}
