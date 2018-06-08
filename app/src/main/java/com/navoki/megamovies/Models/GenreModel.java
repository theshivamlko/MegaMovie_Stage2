package com.navoki.megamovies.Models;

import android.arch.persistence.room.ColumnInfo;

/**
 * Created by Shivam Srivastava on 6/6/2018.
 */
public class GenreModel {
    @ColumnInfo(name = "gid")
    private String id;
    @ColumnInfo(name = "gname")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
