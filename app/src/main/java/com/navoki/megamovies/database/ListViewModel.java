package com.navoki.megamovies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.navoki.megamovies.database.AppDatabase;
import com.navoki.megamovies.models.MovieData;

import java.util.List;

/**
 * Created by Shivam Srivastava on 6/9/2018.
 */
public class ListViewModel extends AndroidViewModel {

    private LiveData<List<MovieData>> listLiveData;
    private AppDatabase appDatabase;

    public ListViewModel(@NonNull Application application) {
        super(application);
        Log.e("MainVie", "Retive List");
        appDatabase = AppDatabase.getInstance(this.getApplication());
        listLiveData = appDatabase.movieDao().getMovieTaskList();
    }

    public LiveData<List<MovieData>> getListLiveData() {
        return listLiveData;
    }

    public void insertListLiveData(List<MovieData> list) {
        appDatabase.movieDao().insertAll(list);

    }
}
