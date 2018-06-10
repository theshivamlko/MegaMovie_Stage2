package com.navoki.megamovies.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.navoki.megamovies.adapters.MovieListAdapter;
import com.navoki.megamovies.BuildConfig;
import com.navoki.megamovies.callbacks.OnAdapterListener;
import com.navoki.megamovies.database.AppDatabase;
import com.navoki.megamovies.models.MovieData;
import com.navoki.megamovies.R;
import com.navoki.megamovies.utils.AppConstants;
import com.navoki.megamovies.utils.Global;
import com.navoki.megamovies.database.ListViewModel;
import com.navoki.megamovies.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListActivity extends AppCompatActivity implements OnAdapterListener {

    @BindView(R.id.ryc_movie_list)
    RecyclerView rycMovieList;
    private Context context;
    private Global global;
    private int paging = 1;
    private ArrayList<MovieData> movieList;
    private MovieListAdapter movieListAdapter;
    private ProgressDialog progressDialog;
    private String mainURL;
    private ListViewModel viewModel;
    private StringRequest stringRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        ButterKnife.bind(this);
        context = this;
        global = Global.getAppInstance();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        rycMovieList.setLayoutManager(gridLayoutManager);

        movieList = new ArrayList<>();
        mainURL = AppConstants.API_MOVIE_POPULAR_LIST;

        rycMovieList.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int lastVisibleItemPosition = ((LinearLayoutManager) rycMovieList.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                Log.e("Pafi", paging + " " + lastVisibleItemPosition + " " + (movieListAdapter.getItemCount()));
                if (lastVisibleItemPosition == movieListAdapter.getItemCount() - 1) {
                    ++paging;
                    getMovieList();
                }
            }
        });

        mainURL = global.getSortBy();
        paging = global.getPaging();
        setUpViewModel();
    }


    /*
     * get list of latest movies
     * */
    private void getMovieList() {

        progressDialog.show();
        Uri.Builder params = new Uri.Builder();
        params.appendQueryParameter(getString(R.string.key_api_key), BuildConfig.API_KEY);
        params.appendQueryParameter(getString(R.string.key_page_no), String.valueOf(paging));
        Log.e("URL", mainURL + params);

        stringRequest = new StringRequest(Request.Method.GET, mainURL + params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray(getString(R.string.key_results));
                    Log.e("URL2", stringRequest.getUrl());
                    if (dataArray.length() > 0) {
                        if (paging == 1)
                            AppDatabase.getInstance(context).movieDao().deleteAll();
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<MovieData>>() {
                        }.getType();
                        ArrayList<MovieData> list = gson.fromJson(dataArray.toString(), listType);
                        viewModel.insertListLiveData(list);

                    } else
                        Toast.makeText(context, getString(R.string.somethingGoneWrong), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    resetPaging();
                    progressDialog.dismiss();
                    Toast.makeText(context, getString(R.string.somethingGoneWrong), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resetPaging();
                progressDialog.dismiss();
                Util.showError(error, context);
            }
        });
        global.addToRequestQueue(stringRequest);
    }

    private void setUpViewModel() {
        viewModel = ViewModelProviders.of((FragmentActivity) context).get(ListViewModel.class);
        viewModel.getListLiveData().observe(MovieListActivity.this, new Observer<List<MovieData>>() {
            @Override
            public void onChanged(@Nullable List<MovieData> movieDataList) {
                Log.e("List", movieList.size() + "--" + (movieDataList.size() - 1));
                if (movieDataList.size() != 0) {
                    movieList.addAll(movieDataList.subList(movieList.size(), movieDataList.size() - 1));
                    Log.e("List2", movieList.size() + "===");
                    if (movieListAdapter == null) {
                        movieListAdapter = new MovieListAdapter(context, movieList);
                        rycMovieList.setAdapter(movieListAdapter);
                    } else
                        movieListAdapter.notifyDataSetChanged();
//Avengers: Infinity War 0 299536
                    global.savePaging(AppConstants.SHAREDPREF_KEY_PAGE, paging);
                } else
                    getMovieList();

            }
        });
    }

    private void resetPaging() {
        if (paging == 0)
            paging = 1;
        else
            --paging;
    }


    @Override
    public synchronized void getNextPagingData() {

    }

    @Override
    public void moveToDetailsScreen(ImageView imageView, MovieData movieData) {

        Intent intent = new Intent(MovieListActivity.this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.EXTRA_MOVIE_DATA, movieData);
        intent.putExtras(bundle);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(MovieListActivity.this,
                        imageView,
                        getString(R.string.poster_transition_name));
        startActivity(intent, options.toBundle());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        movieList = new ArrayList<>();
        if (id == R.id.most_popular) {
            mainURL = AppConstants.API_MOVIE_POPULAR_LIST;
            paging = 1;
            movieList = new ArrayList<>();
            movieListAdapter = null;
            global.saveSortBy(mainURL);
            getMovieList();

        } else if (id == R.id.high_rate) {
            mainURL = AppConstants.API_MOVIE_HIGH_RATE_LIST;
            paging = 1;
            movieList = new ArrayList<>();
            movieListAdapter = null;
            global.saveSortBy(mainURL);
            getMovieList();

        } else if (id == R.id.favorites) {
            Util.finishEntryAnimation(context, new Intent(context, BookmarksActivity.class));
        }
        return true;
    }
}
