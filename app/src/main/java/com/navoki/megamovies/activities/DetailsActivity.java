package com.navoki.megamovies.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.navoki.megamovies.BuildConfig;
import com.navoki.megamovies.R;
import com.navoki.megamovies.adapters.CastListAdapter;
import com.navoki.megamovies.adapters.ReviewsListAdapter;
import com.navoki.megamovies.adapters.TrailerPagerAdapter;
import com.navoki.megamovies.database.AppDatabase;
import com.navoki.megamovies.database.MovieViewModel;
import com.navoki.megamovies.database.MovieViewModelFactory;
import com.navoki.megamovies.models.BookmarkData;
import com.navoki.megamovies.models.CastModel;
import com.navoki.megamovies.models.GenreModel;
import com.navoki.megamovies.models.MovieData;
import com.navoki.megamovies.models.ReviewModel;
import com.navoki.megamovies.utils.AppConstants;
import com.navoki.megamovies.utils.AppExecutors;
import com.navoki.megamovies.utils.Global;
import com.navoki.megamovies.utils.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.frame_banner)
    ImageView frameBanner;
    @BindView(R.id.rel_frame)
    RelativeLayout relFrame;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_poster)
    ImageView imgPoster;
    @BindView(R.id.castExpandArrow)
    ImageView castExpandArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_genre)
    TextView tvGenre;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.label_description)
    TextView labelDescription;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.label_cast)
    TextView labelCast;
    @BindView(R.id.castList)
    RecyclerView castList;
    @BindView(R.id.reviewList)
    RecyclerView reviewList;
    @BindView(R.id.trailerPager)
    ViewPager trailerPager;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    private MovieData movieData;
    private Context context;
    private Global global;
    private String videoKey;
    private ProgressDialog progressDialog;
    private TrailerPagerAdapter sectionsPagerAdapter;
    private AppDatabase appDatabase;
    private boolean isFavorite;
    private MenuItem bookmarkMenuItem;
    private MovieViewModel viewModel;
    private boolean firsTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        context = this;
        global = Global.getAppInstance();
        appDatabase = AppDatabase.getInstance(context);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        castList.setNestedScrollingEnabled(false);

        movieData = new MovieData();
        if (getIntent() != null) {
            Intent intent = getIntent();
            movieData.setId(intent.getExtras().getString(AppConstants.EXTRA_MOVIE_DATA));
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        castList.setLayoutManager(gridLayoutManager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        reviewList.setLayoutManager(linearLayoutManager);
        setUpViewModel();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // set icon after option is being created in onCreateOptionMenu()
                checkBookmarkIcon();
            }
        }, 800);
    }


    private void populateUI() {
        Picasso.get().load(BuildConfig.POSTER_BASE_URL + movieData.getPoster_path())
                .placeholder(R.drawable.placeholder_image)
                .into(imgPoster);
        tvTitle.setText(movieData.getTitle());

        Picasso.get().load((BuildConfig.POSTER_BASE_URL_HIGH + movieData.getBackdrop_path()).trim())
                .placeholder(R.drawable.placeholder_banner)
                .into(imgBanner);
        StringBuilder stringBuilder = new StringBuilder();
        if (null != movieData.getGenreList()) {
            for (GenreModel genreModel : movieData.getGenreList()) {
                stringBuilder.append(genreModel.getName());
                stringBuilder.append(",");
            }
            tvGenre.setText(stringBuilder.toString().substring(0, stringBuilder.length() - 1));
        }
        tvDate.setText(movieData.getRelease_date());
        tvRate.setText(movieData.getVote_average());
        tvDescription.setText(movieData.getOverview());
    }

    // Click on top banner
    @OnClick(R.id.rel_frame)
    void openYoutubeLink() {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.uri_key_youtube) + videoKey));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(BuildConfig.YOUTUBE_URL + videoKey));

        if (appIntent.resolveActivity(getPackageManager()) != null)
            startActivity(appIntent);
        else if (webIntent.resolveActivity(getPackageManager()) != null)
            startActivity(webIntent);
        else
            Toast.makeText(context, getString(R.string.no_app_found), Toast.LENGTH_SHORT).show();
    }

    // Expand cast list
    @OnClick(R.id.castExpandArrow)
    void expandCastList() {
        if (!castList.isNestedScrollingEnabled()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            castList.setLayoutParams(params);
            castList.setNestedScrollingEnabled(true);
            castExpandArrow.setImageResource(R.drawable.ic_arrow_up);
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) getResources().getDimension(R.dimen.list_height));
            castList.setLayoutParams(params);
            castList.setNestedScrollingEnabled(false);
            castExpandArrow.setImageResource(R.drawable.ic_arrow_down);
        }
    }

    @OnClick(R.id.floatingActionButton)
    void shareLink() {

        if (movieData.getTrailer() != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getString(R.string.checkout));
            stringBuilder.append(" ");
            stringBuilder.append(movieData.getTitle());
            stringBuilder.append("\n" + movieData.getTrailer());
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());
            sendIntent.setType(getString(R.string.intent_type));
            if (sendIntent.resolveActivity(getPackageManager()) != null)
                startActivity(Intent.createChooser(sendIntent, getString(R.string.share_trailer)));
            else
                Toast.makeText(context, getString(R.string.no_app_found), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(context, getString(R.string.no_link_found), Toast.LENGTH_SHORT).show();
    }

    /*
     * get movie details
     * */
    private void getMovieDetails() {
        progressDialog.show();
        Uri.Builder params = new Uri.Builder();
        params.appendEncodedPath(movieData.getId());
        params.appendQueryParameter(getString(R.string.key_api_key), BuildConfig.API_KEY);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConstants.API_MOVIE_DETAILS + params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        movieData = gson.fromJson(response, MovieData.class);
                        viewModel.setMovieLiveData(movieData);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Util.showError(error, context);
            }
        });
        global.addToRequestQueue(stringRequest);
    }

    /*
     * get movie trailer
     * */
    private void getMovieTrailer() {
        Uri.Builder params = new Uri.Builder();
        params.appendEncodedPath(movieData.getId());
        params.appendEncodedPath(getString(R.string.key_video));
        params.appendQueryParameter(getString(R.string.key_api_key), BuildConfig.API_KEY);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConstants.API_MOVIE_DETAILS + params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray(getString(R.string.key_results));
                            if (jsonArray.length() > 0) {
                                videoKey = jsonArray.getJSONObject(0).getString(getString(R.string.key_key));
                                movieData.setTrailer(BuildConfig.YOUTUBE_URL + videoKey);
                                ArrayList<String> youtubeKeyList = new ArrayList<>();
                                int i = 0;
                                while (i < jsonArray.length()) {
                                    youtubeKeyList.add(jsonArray.getJSONObject(i).getString(getString(R.string.key_key)));
                                    i++;
                                }
                                movieData.setTrailerList(youtubeKeyList);
                                viewModel.setMovieLiveData(movieData);
                                populateViewPager();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showError(error, context);
            }
        });
        global.addToRequestQueue(stringRequest);
    }


    /*
     * get movie trailer
     * */
    private void getReviews() {
        Uri.Builder params = new Uri.Builder();
        params.appendEncodedPath(movieData.getId());
        params.appendEncodedPath(getString(R.string.key_reviews));
        params.appendQueryParameter(getString(R.string.key_api_key), BuildConfig.API_KEY);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConstants.API_MOVIE_DETAILS + params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray(getString(R.string.key_results));
                            if (jsonArray.length() > 0) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<ArrayList<ReviewModel>>() {
                                }.getType();
                                ArrayList<ReviewModel> reviewArrayList = gson.fromJson(jsonArray.toString(), listType);
                                movieData.setReviewsList(reviewArrayList);
                                viewModel.setMovieLiveData(movieData);
                                populateReviews();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showError(error, context);
            }
        });
        global.addToRequestQueue(stringRequest);
    }


    /*
     * get list of cast in movies
     * */
    private void getMovieCast() {
        Uri.Builder params = new Uri.Builder();
        params.appendEncodedPath(movieData.getId());
        params.appendEncodedPath(getString(R.string.key_casts));
        params.appendQueryParameter(getString(R.string.key_api_key), BuildConfig.API_KEY);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConstants.API_MOVIE_DETAILS
                + params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray(getString(R.string.key_cast));
                    if (dataArray.length() > 0) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<CastModel>>() {
                        }.getType();
                        ArrayList<CastModel> list = gson.fromJson(dataArray.toString(), listType);
                        movieData.setCastList(list);
                        viewModel.setMovieLiveData(movieData);
                        populateCastList();
                    } else
                        Toast.makeText(context, getString(R.string.somethingGoneWrong), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showError(error, context);
            }
        });
        global.addToRequestQueue(stringRequest);
    }

    private void populateCastList() {
        CastListAdapter castListAdapter = new CastListAdapter(context,
                new ArrayList<CastModel>(movieData.getCastList()));
        castList.setAdapter(castListAdapter);
    }

    private void populateViewPager() {
        designViewPager();
        videoKey = movieData.getTrailerList().get(0);
        sectionsPagerAdapter = new TrailerPagerAdapter(getSupportFragmentManager(), movieData.getTrailerList());
        trailerPager.setAdapter(sectionsPagerAdapter);
    }

    private void populateReviews() {
        ReviewsListAdapter reviewsListAdapter = new ReviewsListAdapter(context, movieData.getReviewsList());
        reviewList.setAdapter(reviewsListAdapter);
    }

    private void designViewPager() {
        trailerPager.setClipToPadding(false);
        trailerPager.setPadding(80, 0, 80, 0);

        trailerPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();

                if (position <= 1) {
                    float scaleFactor = Math.max(0.85f, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0) {
                        view.setTranslationX(horzMargin - vertMargin / 2);
                    }
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                }
            }
        });
    }

    private void toggleBookmark() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (isFavorite)
                    appDatabase.favoriteDao().delete(movieData.getId());
                else {
                    Gson gson = new Gson();
                    String json = gson.toJson(movieData);
                    BookmarkData bookmarkData = gson.fromJson(json, BookmarkData.class);
                    appDatabase.favoriteDao().insert(bookmarkData);
                }
                isFavorite = !isFavorite;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkBookmarkIcon();
                    }
                });
            }
        });
    }


    private void checkBookmarkIcon() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final BookmarkData data = appDatabase.favoriteDao().checkFav(movieData.getId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (data != null) {
                            bookmarkMenuItem.setIcon(R.drawable.ic_star_full);
                            isFavorite = true;
                        } else
                            bookmarkMenuItem.setIcon(R.drawable.ic_star_border);
                    }
                });
            }
        });
    }

    private void setUpViewModel() {
        MovieViewModelFactory factory = new MovieViewModelFactory(appDatabase, movieData.getId());
        viewModel = ViewModelProviders.of((FragmentActivity) context, factory).get(MovieViewModel.class);
        viewModel.getMovieLiveData().observe(DetailsActivity.this, new Observer<MovieData>() {
            @Override
            public void onChanged(@Nullable MovieData data) {

                if (null != data) {
                    DetailsActivity.this.movieData = data;
                    if (firsTime)
                        populateUI();
                    if (movieData.getTrailerList() == null)
                        getMovieTrailer();
                    else
                        populateViewPager();

                    if (movieData.getCastList() == null)
                        getMovieCast();
                    else
                        populateCastList();

                    if (movieData.getReviewsList() == null)
                        getReviews();
                    else
                        populateReviews();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        bookmarkMenuItem = menu.findItem(R.id.bookmark);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bookmark) {
            toggleBookmark();
        } else if (id == android.R.id.home) {
            supportFinishAfterTransition();
        }
        return true;
    }
}
