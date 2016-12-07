package com.example.fiddlest.popularmovie_v3.data;

import com.example.fiddlest.popularmovie_v3.data.DataModule;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

/**
 * Created by fiddlest on 2016-04-03.
 */
@Singleton
@Component(modules = {DataModule.class})
public interface DataComponent {
    MovieDataService getMovieDataService();
    Realm realm();

}
