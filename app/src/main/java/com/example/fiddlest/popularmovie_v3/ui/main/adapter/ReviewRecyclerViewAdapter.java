package com.example.fiddlest.popularmovie_v3.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.fiddlest.popularmovie_v3.R;
import com.example.fiddlest.popularmovie_v3.data.model.Review;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fiddlest on 2016-03-24.
 */
public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder> {
    private ArrayList<Review> mReviews = new ArrayList<>();

    private  Context mContext;
    public static class ReviewViewHolder extends  RecyclerView.ViewHolder{
         @Bind(R.id.reviewAuthor) TextView reviewAuthor;
         @Bind(R.id.reviewContent) TextView reviewContent;
         @Bind(R.id.reviewUrl) TextView reviewUrl;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setView(Review review) {
            reviewAuthor.setText(review.getAuthor());
            reviewContent.setText(review.getContent());
            reviewUrl.setMovementMethod(LinkMovementMethod.getInstance());
            reviewUrl.setClickable(true);
            reviewUrl.setText(Html.fromHtml("<a href=" + review.getUrl() + "> Full Review</a>"));
        }
    }

    public ReviewRecyclerViewAdapter(ArrayList<Review> reviews) {
        this.mReviews = reviews;
    }


    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_review, parent, false);
        mContext = parent.getContext();
        return new ReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.setView(review);
    }


    @Override
    public int getItemCount() {
        return mReviews.size();
    }

}
