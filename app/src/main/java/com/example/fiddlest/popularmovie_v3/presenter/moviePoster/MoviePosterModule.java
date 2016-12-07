package com.example.fiddlest.popularmovie_v3.presenter.moviePoster;


import android.content.Context;
import com.example.fiddlest.popularmovie_v3.MovieModule;
import com.example.fiddlest.popularmovie_v3.R;
import com.example.fiddlest.popularmovie_v3.data.MovieDataService;
import com.example.fiddlest.popularmovie_v3.data.model.Movie;
import com.example.fiddlest.popularmovie_v3.ui.main.adapter.MoviePosterAdapter;
import java.util.ArrayList;
import dagger.Module;
import dagger.Provides;

/**
 * Created by fiddlest on 2016-04-04.
 */
@Module
public class MoviePosterModule {

    private MoviePosterView mMoviePosterView;

    public MoviePosterModule(MoviePosterView moviePosterView){
        this.mMoviePosterView = moviePosterView;
    }

    @Provides
    public MoviePosterView provideMovePosterView(){
        return mMoviePosterView;
    }

    @Provides
    public MoviePosterPresenter provideMoviePosterPresenter(MoviePosterView moviePosterView, MovieModule.MovieApiService movieApiService, MovieDataService movieDataService){
        return new MoviePosterPresenterImpl(moviePosterView,movieApiService,movieDataService);
    }
    @Provides
    public MoviePosterAdapter provideMoviePosterAdapter(Context context){
        return new MoviePosterAdapter(context, R.layout.movie_poster_imageview, new ArrayList<Movie>());
    }

}
