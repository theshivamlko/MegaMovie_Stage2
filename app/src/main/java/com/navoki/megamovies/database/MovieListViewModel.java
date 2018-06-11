package com.navoki.megamovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.navoki.megamovies.models.MovieData;

/**
 * Created by Shivam Srivastava on 6/10/2018.
 */
public class MovieListViewModel extends ViewModel {
    private LiveData<MovieData> movieLiveData;
    private AppDatabase appDatabase;
    public MovieListViewModel(AppDatabase appDatabase, String id) {
        this.appDatabase=appDatabase;
        movieLiveData=appDatabase.movieDao().getMovie(id);
    }

    public LiveData<MovieData> getMovieLiveData() {
        return movieLiveData;
    }

    public void setMovieLiveData(MovieData movieData) {
        appDatabase.movieDao().updateMovie(movieData);    }
}
