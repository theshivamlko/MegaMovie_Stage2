package com.navoki.megamovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.navoki.megamovies.models.MovieData;

import java.util.List;

/**
 * Created by Shivam Srivastava on 6/10/2018.
 */
public class MovieListViewModel extends ViewModel {
     private LiveData<List<MovieData>> moveList;
    private AppDatabase appDatabase;
    private String sortBy;

    public MovieListViewModel(AppDatabase appDatabase, String sortBy) {
        this.appDatabase = appDatabase;
         moveList = appDatabase.movieDao().getMovieTaskList(sortBy);
    }

    public LiveData<List<MovieData>> getMovieLiveData() {
        return moveList;
    }


}
