package com.example.fiddlest.popularmovie_v3.presenter.moviePoster;

import android.util.Log;
import com.example.fiddlest.popularmovie_v3.BuildConfig;
import com.example.fiddlest.popularmovie_v3.MovieModule;
import com.example.fiddlest.popularmovie_v3.data.MovieDataService;
import com.example.fiddlest.popularmovie_v3.data.model.Movie;
import com.example.fiddlest.popularmovie_v3.data.model.MoviesWithMeta;
import com.example.fiddlest.popularmovie_v3.data.model.RealmMovie;
import java.util.ArrayList;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by fiddlest on 2016-04-04.
 */
public class MoviePosterPresenterImpl implements MoviePosterPresenter {


    private MoviePosterView mMoviePosterView;
    private MovieModule.MovieApiService mMovieApiService;
    private MovieDataService mMovieDataService;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private MoviesWithMeta mMoviesWithMeta;
    private final String TAG = "MoviePosterPresenter";

    MoviePosterPresenterImpl(MoviePosterView moviePosterView, MovieModule.MovieApiService movieApiService, MovieDataService movieDataService) {
        this.mMoviePosterView = moviePosterView;
        this.mMovieApiService = movieApiService;
        this.mMovieDataService = movieDataService;
    }

    public <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void loadMovieData(String sortBy, int page) {
        if (sortBy.equals("favorite")) {
            this.loadFavoriteMovie("id");
        } else {
            this.loadMovieDataFromServer(sortBy, page);
        }
    }

    public void loadFavoriteMovie(String sortBy) {
        mMoviesWithMeta = new MoviesWithMeta();
        Observable<RealmResults<RealmMovie>> realmMovies = mMovieDataService.getMovies("id");
        ArrayList<Movie> movieList= new ArrayList<>();
        realmMovies
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        realmResults -> {
                            Log.d("RealmResult",realmResults.toString()+'t');
                            for (RealmMovie realmMovie : realmResults) {
                                movieList.add(new Movie(realmMovie));
                            }
                            mMoviesWithMeta.setResults(movieList);
                            mMoviePosterView.movieLoadCompleted(movieList,true);
                        },
                        error -> {
                            Log.d(TAG,error.toString());
                        }
                );
    }

    public void loadMovieDataFromServer(String sortBy, int page) {
        Observable movieObservable = mMovieApiService.getMovieList(sortBy, BuildConfig.MOVIE_DB_API_KEY, page);
        Subscription subscription = movieObservable
                .compose(applySchedulers())
                .subscribe(new Observer<MoviesWithMeta>() {
            @Override
            public void onCompleted() {
                mMoviePosterView.movieLoadCompleted(mMoviesWithMeta.getResults(),false);
            }
            public void onError(Throwable e) {
                Log.d(TAG, "Error :" + e.toString());
            }

            @Override
            public void onNext(MoviesWithMeta tmpMoviesWithMeta) {
                mMoviesWithMeta = tmpMoviesWithMeta;
                Log.d(TAG, tmpMoviesWithMeta.toString());
            }
        });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void onDestroy() {
        mCompositeSubscription.unsubscribe();
    }
}
