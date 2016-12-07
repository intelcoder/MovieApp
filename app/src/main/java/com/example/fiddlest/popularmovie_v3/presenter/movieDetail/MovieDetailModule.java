package com.example.fiddlest.popularmovie_v3.presenter.movieDetail;


import com.example.fiddlest.popularmovie_v3.MovieModule;
import com.example.fiddlest.popularmovie_v3.data.MovieDataService;
import dagger.Module;
import dagger.Provides;


/**
 * Created by fiddlest on 2016-04-10.
 */

@Module
public class MovieDetailModule {
    public MovieDetailView mMovieDetailView;

    public MovieDetailModule(MovieDetailView movieDetailView){
        this.mMovieDetailView = movieDetailView;
    }
    @Provides
    public MovieDetailView provideMovieDetailView(){
        return mMovieDetailView;
    }

    @Provides
    public MovieDetailPresenter provideMovieDetailPresenter(MovieDetailView movieDetailView, MovieModule.MovieApiService movieApiService, MovieDataService movieDataService){
        return new MovieDetailPresenterImpl(movieDetailView, movieApiService,movieDataService);
    }
}
