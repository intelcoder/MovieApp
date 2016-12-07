package com.example.fiddlest.popularmovie_v3.presenter.movieDetail;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import com.example.fiddlest.popularmovie_v3.BuildConfig;
import com.example.fiddlest.popularmovie_v3.MovieModule;
import com.example.fiddlest.popularmovie_v3.data.MovieDataService;
import com.example.fiddlest.popularmovie_v3.data.model.Movie;
import com.example.fiddlest.popularmovie_v3.data.model.RealmMovie;
import com.example.fiddlest.popularmovie_v3.data.model.Trailer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by fiddlest on 2016-04-10.
 */
public class MovieDetailPresenterImpl implements  MovieDetailPresenter {

    public CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private MovieDetailView mMovieDetailView;
    private MovieModule.MovieApiService mMovieApiService;
    private MovieDataService mMovieDataService;
    private Realm mRealm;

    private Movie mMovie;
    public MovieDetailPresenterImpl(MovieDetailView movieDetailView, MovieModule.MovieApiService movieApiService, MovieDataService movieDataService){
        this.mMovieDetailView =movieDetailView;
        this.mMovieApiService = movieApiService;
        this.mMovieDataService = movieDataService;
    }


    public <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     *
     * This function loads movie detail either from db or server depends on internet connection state
     * @param isNetworkUp
     */
    @Override
    public void getMovieDetail(boolean isNetworkUp) {

        //findFirst throw null exception if id is not exist
        RealmResults<RealmMovie> realmMovie = mMovieDataService.getMovieById(mMovie.getId());
        if(realmMovie.size()>0){
            this.mMovieDetailView.fillStar();
            this.mMovie.setFavorite(true);
        }
        if(realmMovie.size()>0 && !isNetworkUp){
            mMovie = new Movie(realmMovie.first());
            this.mMovieDetailView.showMovieDetail(this.mMovie);
        }else{
            Subscription subscription = mMovieApiService
                    .getMovieDetail(this.mMovie.getId(),BuildConfig.MOVIE_DB_API_KEY)
                    .compose(this.applySchedulers())
                    .subscribe(movie -> {
                                //If trailers are more than 2, take only first 2
                                if (movie.getTrailerContainer().getTrailers().size() > 2) {
                                    ArrayList<Trailer> tmp = movie.getTrailerContainer().getTrailers();
                                    ArrayList<Trailer> trailers = new ArrayList<>();
                                    for (int i = 0; i < 2; i++) {
                                        trailers.add(tmp.get(i));
                                    }
                                    movie.setTrailers(trailers);
                                }
                                this.mMovie = movie;
                            },
                            error -> Log.d("EEEE", "getMovieDetail" + error.toString()),
                            () -> this.mMovieDetailView.showMovieDetail(this.mMovie));
            mCompositeSubscription.add(subscription);
        }
    }

    @Override
    public void saveFavoriteMovie() {
        //Get file Path
        String filePath = android.os.Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES)+mMovie.getPoster_path();
        mMovie.setPoster_path(filePath);

       this.mMovieDataService.saveMovie(mMovie);
    }

    @Override
    public Movie getMovie() {
        return mMovie;
    }


    @Override
    public void setMovie(Movie movie) {
        this.mMovie = movie;
    }

    /**
     * This function return picasso target obj which has code to save img
     * @param poster
     * @return
     */
    @Override
    public Target getPicassoTarget(File poster) {
        return new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {
                try {
                    poster.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(poster);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
    }
    @Override
    public void onDestroy() {
        mCompositeSubscription.unsubscribe();
    }

}
