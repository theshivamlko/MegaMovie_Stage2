package com.navoki.megamovies.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.navoki.megamovies.R;
import com.navoki.megamovies.adapters.BookmarkListAdapter;
import com.navoki.megamovies.callbacks.OnBookmarkAdapterListener;
import com.navoki.megamovies.database.BookmarkViewModel;
import com.navoki.megamovies.models.BookmarkData;
import com.navoki.megamovies.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarksActivity extends AppCompatActivity implements OnBookmarkAdapterListener {

    @BindView(R.id.ryc_movie_list)
    RecyclerView rycMovieList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_list);

        ButterKnife.bind(this);
        context = this;
        getSupportActionBar().setTitle(getString(R.string.bookmarks));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        rycMovieList.setLayoutManager(gridLayoutManager);
        setUpViewModel();
    }

    private void setUpViewModel() {
        BookmarkViewModel bookmarkListViewModel = ViewModelProviders.of((FragmentActivity) context).get(BookmarkViewModel.class);
        bookmarkListViewModel.getFavListLiveData().observe(BookmarksActivity.this, new Observer<List<BookmarkData>>() {
            @Override
            public void onChanged(@Nullable List<BookmarkData> movieDataList) {
                BookmarkListAdapter bookmarkListAdapter = new BookmarkListAdapter(context, new ArrayList<>(movieDataList));
                rycMovieList.setAdapter(bookmarkListAdapter);
                if (movieDataList.size() == 0)
                    Toast.makeText(context, getString(R.string.noDataFound), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void moveToDetailsScreen(ImageView imageView, BookmarkData bookmarkData) {

        Intent intent = new Intent(BookmarksActivity.this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.EXTRA_MOVIE_DATA, bookmarkData.getId());
        intent.putExtras(bundle);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(BookmarksActivity.this,
                        imageView,
                        getString(R.string.poster_transition_name));
        startActivity(intent, options.toBundle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_to_right);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_to_right);
    }
}
