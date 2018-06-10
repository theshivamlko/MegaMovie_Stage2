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
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.navoki.megamovies.adapters.MovieListAdapter;
import com.navoki.megamovies.callbacks.OnAdapterListener;
import com.navoki.megamovies.models.MovieData;
import com.navoki.megamovies.R;
import com.navoki.megamovies.utils.AppConstants;
import com.navoki.megamovies.database.BookmarkListViewModel;
import com.navoki.megamovies.database.ListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarksActivity extends AppCompatActivity implements OnAdapterListener {

    @BindView(R.id.ryc_movie_list)
    RecyclerView rycMovieList;
    private Context context;
    private ListViewModel viewModel;

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
        BookmarkListViewModel bookmarkListViewModel = ViewModelProviders.of((FragmentActivity) context).get(BookmarkListViewModel.class);
        bookmarkListViewModel.getFavListLiveData().observe(BookmarksActivity.this, new Observer<List<MovieData>>() {
            @Override
            public void onChanged(@Nullable List<MovieData> movieDataList) {
                MovieListAdapter movieListAdapter = new MovieListAdapter(context, new ArrayList<MovieData>(movieDataList));
                rycMovieList.setAdapter(movieListAdapter);
                Log.e("Book", movieDataList.size() + " book");
                for(MovieData movieData:movieDataList)
                    Log.e("Movie",movieData.getIsfavorite()+" "+movieData.getTitle());
                if (movieDataList.size() == 0)
                    Toast.makeText(context, getString(R.string.noDataFound), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getNextPagingData() {
    }

    @Override
    public void moveToDetailsScreen(ImageView imageView, MovieData movieData) {

        Intent intent = new Intent(BookmarksActivity.this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.EXTRA_MOVIE_DATA, movieData);
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
