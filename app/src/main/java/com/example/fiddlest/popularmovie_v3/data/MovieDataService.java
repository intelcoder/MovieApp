package com.example.fiddlest.popularmovie_v3.data;


import com.example.fiddlest.popularmovie_v3.data.model.Movie;
import com.example.fiddlest.popularmovie_v3.data.model.RealmMovie;

import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by fiddlest on 2016-04-11.
 */
public interface MovieDataService {
    void saveMovie(Movie movie);
    RealmResults<RealmMovie> getMovieById(int id);
    Observable<RealmResults<RealmMovie>> getMovies(String orderBy);
}
