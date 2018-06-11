package com.navoki.megamovies.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.navoki.megamovies.database.CastsConvertor;
import com.navoki.megamovies.database.GenreConvertor;
import com.navoki.megamovies.database.ReviewConvertor;
import com.navoki.megamovies.database.TrailerConvertor;

import java.util.List;

/**
 * Created by Shivam Srivastava on 6/5/2018.
 */
@Entity(tableName = "movies")
public class MovieData implements Parcelable {
/*

    @PrimaryKey(autoGenerate = true)
    private int uid;
*/

    @PrimaryKey
    @NonNull
    private String id;
    private String title, overview, release_date, poster_path, vote_average,
            original_language, backdrop_path, trailer;

    private int isfavorite;
    private String sortby;


    @ColumnInfo(name = "genrelist")
    @TypeConverters(GenreConvertor.class)
    private List<GenreModel> genres;

    @ColumnInfo(name = "casts")
    @TypeConverters(CastsConvertor.class)
    private List<CastModel> castList;

    @ColumnInfo(name = "reviews")
    @TypeConverters(ReviewConvertor.class)
    private List<ReviewModel> reviewsList;

    @ColumnInfo(name = "trailerlist")
    @TypeConverters(TrailerConvertor.class)
    private List<String> trailerList;

    public MovieData() {
    }

   /* public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }*/

    public int getIsfavorite() {
        return isfavorite;
    }

    public void setIsfavorite(int isfavorite) {
        this.isfavorite = isfavorite;
    }

    public String getSortby() {
        return sortby;
    }

    public void setSortby(String sortby) {
        this.sortby = sortby;
    }

    public List<GenreModel> getGenreList() {
        return genres;
    }

    public void setGenreList(List<GenreModel> genres) {
        this.genres = genres;
    }

    public List<ReviewModel> getReviewsList() {
        return reviewsList;
    }

    public List<String> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<String> trailerList) {
        this.trailerList = trailerList;
    }

    public void setReviewsList(List<ReviewModel> reviewsList) {
        this.reviewsList = reviewsList;
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

 /*   public int getIsfavorite() {
        return isfavorite;
    }

    public void setIsfavorite(int isfavorite) {
        this.isfavorite = isfavorite;
    }*/

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public List<CastModel> getCastList() {
        return castList;
    }

    public void setCastList(List<CastModel> castList) {
        this.castList = castList;
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

    @Ignore
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

    @Ignore
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
