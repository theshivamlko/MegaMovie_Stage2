package com.navoki.megamovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.navoki.megamovies.BuildConfig;
import com.navoki.megamovies.models.CastModel;
import com.navoki.megamovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class CastListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<CastModel> castModelArrayList;
    private final Context context;

    class CastHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profile_image)
        CircleImageView profileImage;
        @BindView(R.id.tv_cast_name)
        TextView tvCastName;
        @BindView(R.id.tv_cast_character)
        TextView tvCastCharacter;

        CastHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public CastListAdapter(Context context, ArrayList<CastModel> castModelArrayList) {
        this.castModelArrayList = castModelArrayList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.item_cast, null);
        return new CastHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final CastHolder castHolder = (CastHolder) holder;
        final CastModel castModel = castModelArrayList.get(position);

        Picasso.get().load((BuildConfig.POSTER_BASE_URL + castModel.getProfile_path()).trim())
                .placeholder(R.drawable.placeholder_image)
                .into(castHolder.profileImage);
        castHolder.tvCastName.setText(castModel.getName());
        castHolder.tvCastCharacter.setText(castModel.getCharacter());
    }

    @Override
    public int getItemCount() {
        return castModelArrayList.size();
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
