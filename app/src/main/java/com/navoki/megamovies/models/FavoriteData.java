package com.navoki.megamovies.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Shivam Srivastava on 6/11/2018.
 */
@Entity(tableName = "favorite")
public class FavoriteData {
    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
}
