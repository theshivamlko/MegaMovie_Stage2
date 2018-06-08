package com.navoki.megamovies.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.navoki.megamovies.Database.GenreConvertor;

import java.util.List;

/**
 * Created by Shivam Srivastava on 6/5/2018.
 */
@Entity(tableName = "favorites")
public class MovieData implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String id, title, overview, release_date, poster_path, vote_average,
            original_language, backdrop_path,trailer;

    @ColumnInfo(name = "genrelist")
    @TypeConverters(GenreConvertor.class)
    private List<GenreModel> genres;

    public MovieData() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public List<GenreModel> getGenreList() {
        return genres;
    }

    public void setGenreList(List<GenreModel> genres) {
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<GenreModel> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreModel> genres) {
        this.genres = genres;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    protected MovieData(Parcel in) {
        id = in.readString();
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        vote_average = in.readString();
        original_language = in.readString();
        backdrop_path = in.readString();
        trailer = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(poster_path);
        dest.writeString(vote_average);
        dest.writeString(original_language);
        dest.writeString(backdrop_path);
        dest.writeString(trailer);
    }
}
