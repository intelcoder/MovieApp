package com.example.fiddlest.popularmovie_v3;

import android.content.Context;
import com.example.fiddlest.popularmovie_v3.data.DataModule;
import com.example.fiddlest.popularmovie_v3.data.MovieDataService;
import javax.inject.Singleton;
import dagger.Component;
import io.realm.Realm;

/**
 * Created by fiddlest on 2016-04-02.
 */
@Singleton
@Component(modules = {AppModule.class,NetworkModule.class,DataModule.class,MovieModule.class})
public interface AppComponent {
    void inject(App app);
    MovieModule.MovieApiService getMovieApiService();
    Realm getRealm();
    Context getContext();
    MovieDataService getMovieDataService();
}
