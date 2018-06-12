package com.navoki.megamovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.navoki.megamovies.models.MovieData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shivam Srivastava on 6/10/2018.
 */
public class MovieListViewModel extends ViewModel {
     private MutableLiveData<List<MovieData>> moveList=new MutableLiveData<>();
    private AppDatabase appDatabase;
    private String sortBy;

    public MovieListViewModel(AppDatabase appDatabase, String sortBy) {
        this.appDatabase = appDatabase;
        Log.e("VIemOdel","Creat");
         moveList.postValue(appDatabase.movieDao().getMovieTaskList(sortBy));
    }

    public LiveData<List<MovieData>> getMovieLiveData() {
        Log.e("VIemOdel","get list");
        return moveList;
    }

    public void clear() {
        Log.e("VIemOdel","get list");
          moveList.setValue(new ArrayList<MovieData>());
    }


    public void setMovieLiveList(List<MovieData> data) {
        Log.e("VIemOdel","get list");
        List<MovieData> list=moveList.getValue();
        list.addAll(data);
        moveList.setValue(list);
    }
    public void setNewMovieList(List<MovieData> data) {
        Log.e("VIemOdel","get list");
         moveList.setValue(data);
    }


}
