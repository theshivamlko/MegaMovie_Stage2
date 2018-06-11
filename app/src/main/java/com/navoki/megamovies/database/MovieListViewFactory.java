package com.navoki.megamovies.database;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Created by Shivam Srivastava on 6/10/2018.
 */
public class MovieListViewFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase appDatabase;
    private final String sortBy;

    public MovieListViewFactory(AppDatabase appDatabase, String sortBy) {
        this.appDatabase = appDatabase;
        this.sortBy = sortBy;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieViewModel(appDatabase,id);
    }
}
