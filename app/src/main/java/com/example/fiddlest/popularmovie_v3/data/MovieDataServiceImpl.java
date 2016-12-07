package com.example.fiddlest.popularmovie_v3.data;



import com.example.fiddlest.popularmovie_v3.data.model.Movie;
import com.example.fiddlest.popularmovie_v3.data.model.RealmMovie;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by fiddlest on 2016-04-11.
 */
public class MovieDataServiceImpl implements MovieDataService {
    private Realm mRealm;
    private RealmQuery<RealmMovie> mQuery;


    public MovieDataServiceImpl(Realm realm, RealmQuery<RealmMovie> query) {
        this.mRealm = realm;
        this.mQuery = query;
    }

    @Override
    public void saveMovie(Movie movie) {
        mRealm.beginTransaction();
        RealmMovie realmMovie = new RealmMovie();
        realmMovie.setId(movie.getId());
        realmMovie.setPoster_path(movie.getPoster_path());
        realmMovie.setRelease_date(movie.getRelease_date());
        realmMovie.setOriginal_title(movie.getOriginal_title());
        realmMovie.setVote_average(movie.getVote_average());
        realmMovie.setOverview(movie.getOverview());
        realmMovie.setRuntime(movie.getRuntime());
        mRealm.copyToRealmOrUpdate(realmMovie);
        mRealm.commitTransaction();
        mRealm.close();
    }

    @Override
    public  RealmResults<RealmMovie> getMovieById(int id) {
        return this.getRealmMovieQuery().equalTo("id", id).findAll();
    }

    @Override
    public Observable<RealmResults<RealmMovie>> getMovies(String orderBy) {
        return Realm.getDefaultInstance().allObjects(RealmMovie.class).asObservable();
    }
    private RealmQuery<RealmMovie> getRealmMovieQuery(){
        return Realm.getDefaultInstance().where(RealmMovie.class);
    }
}


