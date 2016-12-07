package com.example.fiddlest.popularmovie_v3.presenter.movieDetail;


import com.example.fiddlest.popularmovie_v3.data.model.Movie;

/**
 * Created by fiddlest on 2016-04-10.
 */
public interface MovieDetailView {
    /**
     * This function fill a star on bar
     */
    void fillStar();

    /**
     * This function show movie info to view
     * @param movie
     */
    void showMovieDetail(Movie movie);
}
