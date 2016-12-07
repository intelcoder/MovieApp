package com.example.fiddlest.popularmovie_v3.presenter.moviePoster;


import com.example.fiddlest.popularmovie_v3.data.model.Movie;

import java.util.ArrayList;

/**
 * Created by fiddlest on 2016-04-04.
 */
public interface MoviePosterView {

    void movieLoadCompleted(ArrayList<Movie> movies, boolean isFavorite);

}
