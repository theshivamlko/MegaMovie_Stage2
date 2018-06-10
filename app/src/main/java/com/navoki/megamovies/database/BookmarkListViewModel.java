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
public class BookmarkListViewModel extends AndroidViewModel {

    private LiveData<List<MovieData>> listLiveData;
    private AppDatabase appDatabase;

    public BookmarkListViewModel(@NonNull Application application) {
        super(application);
        Log.e("MainVie", "Retive List");
        appDatabase = AppDatabase.getInstance(this.getApplication());
        listLiveData = appDatabase.movieDao().getFavoriteList();
    }

    public LiveData<List<MovieData>> getFavListLiveData() {
        return listLiveData;
    }


}
