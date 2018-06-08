package com.navoki.megamovies.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.navoki.megamovies.Adapters.MovieListAdapter;
import com.navoki.megamovies.BuildConfig;
import com.navoki.megamovies.Callbacks.OnAdapterListener;
import com.navoki.megamovies.Models.MovieData;
import com.navoki.megamovies.R;
import com.navoki.megamovies.Utils.AppConstants;
import com.navoki.megamovies.Utils.Global;
import com.navoki.megamovies.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
        getMovieList();
    }


    /*
     * get list of latest movies
     * */
    private synchronized void getMovieList() {

        progressDialog.show();
        Uri.Builder params = new Uri.Builder();
        params.appendQueryParameter(getString(R.string.key_api_key), BuildConfig.API_KEY);
        params.appendQueryParameter(getString(R.string.key_page_no), String.valueOf(paging));

        StringRequest stringRequest = new StringRequest(Request.Method.GET, mainURL + params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray(getString(R.string.key_results));

                    if (dataArray.length() > 0) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<ArrayList<MovieData>>() {
                        }.getType();
                        ArrayList<MovieData> list = gson.fromJson(dataArray.toString(), listType);
                        movieList.addAll(list);
                        if (movieListAdapter == null) {
                            paging = 1;
                            movieListAdapter = new MovieListAdapter(context, movieList);
                            rycMovieList.setAdapter(movieListAdapter);
                        } else
                            movieListAdapter.notifyDataSetChanged();
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

    private void resetPaging() {
        if (paging == 0)
            paging = 1;
        else
            --paging;
    }


    @Override
    public synchronized void getNextPagingData() {
        ++paging;
        getMovieList();
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
            movieListAdapter = null;
            getMovieList();
        } else if (id == R.id.high_rate) {
            mainURL = AppConstants.API_MOVIE_HIGH_RATE_LIST;
            paging = 1;
            movieListAdapter = null;
            getMovieList();
        } else if (id == R.id.favorites) {
            Util.finishEntryAnimation(context, new Intent(context, BookmarksActivity.class));
        }
        return true;
    }
}
