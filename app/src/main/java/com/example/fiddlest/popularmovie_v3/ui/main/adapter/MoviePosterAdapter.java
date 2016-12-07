package com.example.fiddlest.popularmovie_v3.ui.main.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.example.fiddlest.popularmovie_v3.R;
import com.example.fiddlest.popularmovie_v3.data.model.Movie;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by fiddlest on 2016-02-19.
 */
public class MoviePosterAdapter extends ArrayAdapter {

    private Context mContext;
    private int mChildLayoutId;
    private ArrayList<Movie> mMovieInfo;
    private boolean isFavorite;

    public MoviePosterAdapter(Context context, int resource, ArrayList<Movie> movieInfo)  {
        super(context, resource,movieInfo);
        this.mContext = context;
        this.mChildLayoutId = resource;
        this.mMovieInfo=movieInfo;

        setNotifyOnChange(true);

    }
    static class ViewHolder{
        ImageView movieImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(this.mContext).inflate(this.mChildLayoutId, parent, false);
            holder = new ViewHolder();
            holder.movieImage  = (ImageView) view.findViewById(R.id.movieImage);
            if(  holder.movieImage==null){
                throw new NullPointerException("This view is null");
            }
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        String posterUrl = mMovieInfo.get(position).getPoster_path();
        if(isFavorite && posterUrl != null){

            File file = new File(posterUrl);

            //Load image to MainActivity
            Picasso.with(this.mContext)
                    .load(file)
                    .placeholder(R.drawable.movie_icon)
                    .error(R.drawable.movie_icon)
                    .into(holder.movieImage);
        }else{

            //Load image to MainActivity
            Picasso.with(this.mContext)
                    .load(this.mContext.getResources().getString(R.string.movie_data_poster_base_url) + posterUrl)
                    .placeholder(R.drawable.movie_icon)
                    .error(R.drawable.movie_icon)
                    .into(holder.movieImage);
        }


        return view;

    }

    public void  updateData(ArrayList<Movie> newMovieInfo, boolean isFavorite){
        this.mMovieInfo.clear();
        this.mMovieInfo.addAll(newMovieInfo);
        this.isFavorite =isFavorite;
        notifyDataSetChanged();
    }
}
