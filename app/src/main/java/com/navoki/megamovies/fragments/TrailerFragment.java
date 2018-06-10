package com.navoki.megamovies.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.navoki.megamovies.BuildConfig;
import com.navoki.megamovies.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Shivam Srivastava on 6/7/2018.
 */
public class TrailerFragment extends Fragment {

    private static final String ARG_KEY = "youtubeKey";
    @BindView(R.id.video_banner)
    ImageView videoBanner;
    @BindView(R.id.rel_frame)
    RelativeLayout relFrame;
    Unbinder unbinder;
    private String videoKey;

    public TrailerFragment() {
    }

    public static TrailerFragment newInstance(String key) {
        TrailerFragment fragment = new TrailerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        videoKey = getArguments().getString(ARG_KEY);

       // Log.e("Url",BuildConfig.YOUTUBE_TUMBNAIL_URL + videoKey + getString(R.string.param_video_image_ext));
        Picasso.get().load(BuildConfig.YOUTUBE_TUMBNAIL_URL + videoKey + getString(R.string.param_video_image_ext)).resize(300,200)
                .placeholder(R.drawable.placeholder_banner).into(videoBanner);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.rel_frame)
    void openYoutubeLink() {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.uri_key_youtube) + videoKey));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(BuildConfig.YOUTUBE_URL + videoKey));

        if (appIntent.resolveActivity(getContext().getPackageManager()) != null)
            startActivity(appIntent);
        else if (webIntent.resolveActivity(getContext().getPackageManager()) != null)
            startActivity(webIntent);
        else
            Toast.makeText(getContext(), getString(R.string.no_app_found), Toast.LENGTH_SHORT).show();
    }
}
