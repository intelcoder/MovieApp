package com.example.fiddlest.popularmovie_v3.presenter.movieDetail;

import com.example.fiddlest.popularmovie_v3.AppComponent;
import com.example.fiddlest.popularmovie_v3.scope.FragmentScope;
import com.example.fiddlest.popularmovie_v3.ui.main.fragment.MovieDetailFragment;
import dagger.Component;

/**
 * Created by fiddlest on 2016-04-10.
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = MovieDetailModule.class)
public interface MovieDetailComponent {
    void inject(MovieDetailFragment movieDetailFragment);
    MovieDetailPresenter getMovieDetailPresenter();
}
