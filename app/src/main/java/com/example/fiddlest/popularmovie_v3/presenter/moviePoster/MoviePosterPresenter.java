package com.example.fiddlest.popularmovie_v3.presenter.moviePoster;


import com.example.fiddlest.popularmovie_v3.presenter.BasePresenter;

/**
 * Created by fiddlest on 2016-04-04.
 */
public interface MoviePosterPresenter extends BasePresenter<MoviePosterView> {
    void loadMovieData(String sortBy, int page);
    void onDestroy();
}
