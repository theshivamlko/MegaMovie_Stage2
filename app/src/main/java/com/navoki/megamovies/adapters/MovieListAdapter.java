package com.navoki.megamovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.navoki.megamovies.BuildConfig;
import com.navoki.megamovies.callbacks.OnAdapterListener;
import com.navoki.megamovies.models.MovieData;
import com.navoki.megamovies.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<MovieData> movieList;
    private final Context context;
    private final OnAdapterListener adapterListener;
    private int lastPosition = -1;
    public class MovieHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_poster)
        ImageView imgPoster;
        @BindView(R.id.frame)
        FrameLayout frame;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_year)
        TextView tvYear;
        @BindView(R.id.container)
        RelativeLayout container;

        private MovieHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public MovieListAdapter(Context context, ArrayList<MovieData> movieList) {
        this.movieList = movieList;
        this.context = context;
        adapterListener = (OnAdapterListener) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.item_movie_layout, null);
        return new MovieHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final MovieHolder movieHolder = (MovieHolder) holder;
        final MovieData movieData = movieList.get(position);

        Log.e("Adapter",movieData.getTitle()+" "+movieData.getIsfavorite()+" "+movieData.getId());
        movieHolder.tvTitle.setText(movieData.getTitle());
        Picasso.get().load((BuildConfig.POSTER_BASE_URL + movieData.getPoster_path()).trim())
                .placeholder(R.drawable.placeholder_image)
                .into(movieHolder.imgPoster);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date date = simpleDateFormat.parse(movieData.getRelease_date());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            movieHolder.tvYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        movieHolder.imgPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.moveToDetailsScreen(movieHolder.imgPoster, movieData);
            }
        });
        if (position >= movieList.size() - 2)
            adapterListener.getNextPagingData();

        if(position >lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.anim_from_bottom);
            movieHolder.itemView.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
