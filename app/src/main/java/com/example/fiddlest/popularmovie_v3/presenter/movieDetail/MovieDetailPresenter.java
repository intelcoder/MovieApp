package com.example.fiddlest.popularmovie_v3.presenter.movieDetail;

import com.example.fiddlest.popularmovie_v3.data.model.Movie;
import com.example.fiddlest.popularmovie_v3.presenter.BasePresenter;
import com.squareup.picasso.Target;

import java.io.File;

/**
 * Created by fiddlest on 2016-04-10.
 */
public interface MovieDetailPresenter extends BasePresenter<MovieDetailView> {

    void getMovieDetail(boolean isNetworkUp);
    void setMovie(Movie movie);
    void saveFavoriteMovie();
    Movie getMovie();
    Target getPicassoTarget(File poster);
}
