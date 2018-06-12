package com.navoki.megamovies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.navoki.megamovies.models.BookmarkData;

import java.util.List;

/**
 * Created by Shivam Srivastava on 6/9/2018.
 */
public class BookmarkViewModel extends AndroidViewModel {

    private LiveData<List<BookmarkData>> listLiveData;
    private AppDatabase appDatabase;

    public BookmarkViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(this.getApplication());
        listLiveData = appDatabase.favoriteDao().getFavList();
    }

    public LiveData<List<BookmarkData>> getFavListLiveData() {
        return listLiveData;
    }
}
