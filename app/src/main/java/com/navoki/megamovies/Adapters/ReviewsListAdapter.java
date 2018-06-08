package com.navoki.megamovies.Adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.navoki.megamovies.Models.ReviewModel;
import com.navoki.megamovies.R;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReviewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<ReviewModel> reviewList;
    private final Context context;
    private final int[] color = {R.color.colorBlue, R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorGreen,
            R.color.colorRed,R.color.colorAccent};
    private final Random random = new Random();

    static class ReviewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name_logo)
        TextView tvNameLogo;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;

        private ReviewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ReviewsListAdapter(Context context, ArrayList<ReviewModel> reviewList) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.item_review, null);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final ReviewHolder reviewHolder = (ReviewHolder) holder;
        final ReviewModel reviewModel = reviewList.get(position);
        reviewHolder.tvName.setText(reviewModel.getAuthor());
        reviewHolder.tvContent.setText(reviewModel.getContent());

        int n = random.nextInt(5);
        GradientDrawable tempdrawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.circle_shape);
        tempdrawable.setColor(ContextCompat.getColor(context, color[n]));
        reviewHolder.tvNameLogo.setText(reviewModel.getAuthor().substring(0, 1).toUpperCase());
        reviewHolder.tvNameLogo.setBackground(tempdrawable);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
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
