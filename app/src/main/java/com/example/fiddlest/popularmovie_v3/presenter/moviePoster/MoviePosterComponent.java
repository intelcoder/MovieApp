package com.example.fiddlest.popularmovie_v3.presenter.moviePoster;

import com.example.fiddlest.popularmovie_v3.AppComponent;
import com.example.fiddlest.popularmovie_v3.scope.FragmentScope;
import com.example.fiddlest.popularmovie_v3.ui.main.adapter.MoviePosterAdapter;
import com.example.fiddlest.popularmovie_v3.ui.main.fragment.MoviePosterFragment;
import dagger.Component;

/**
 * Created by fiddlest on 2016-04-04.
 */
@FragmentScope
@Component(dependencies = {AppComponent.class},modules = {MoviePosterModule.class})
public interface MoviePosterComponent {
    void inject(MoviePosterFragment moviePosterFragment);
    MoviePosterPresenter getMoviePosterPresenter();
    MoviePosterAdapter getMoviePosterAdapter();

}
