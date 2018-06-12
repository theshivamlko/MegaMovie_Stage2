package com.navoki.megamovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.navoki.megamovies.models.MovieData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shivam Srivastava on 6/10/2018.
 */
public class MovieListViewModel extends ViewModel {
    private MutableLiveData<List<MovieData>> moveList = new MutableLiveData<>();

    public MovieListViewModel(AppDatabase appDatabase, String sortBy) {
        moveList.postValue(appDatabase.movieDao().getMovieTaskList(sortBy));
    }

    public LiveData<List<MovieData>> getMovieLiveData() {
        return moveList;
    }

    public void clear() {
        moveList.setValue(new ArrayList<MovieData>());
    }


    public void setMovieLiveList(List<MovieData> data) {
        List<MovieData> list = moveList.getValue();
        list.addAll(data);
        moveList.setValue(list);
    }

    public void setNewMovieList(List<MovieData> data) {
        moveList.setValue(data);
    }
}
