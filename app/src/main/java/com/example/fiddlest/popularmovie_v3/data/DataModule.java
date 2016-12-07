package com.example.fiddlest.popularmovie_v3.data;



import com.example.fiddlest.popularmovie_v3.data.model.RealmMovie;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmQuery;


/**
 * Created by fiddlest on 2016-04-02.
 */

@Module
public class DataModule {

    @Singleton
    @Provides
    public Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Singleton
    @Provides
    public RealmQuery<RealmMovie> provideRealmQuery(Realm realm) {
        return realm.where(RealmMovie.class);
    }

    @Singleton
    @Provides
    public MovieDataService provideMovieDataService(Realm realm, RealmQuery<RealmMovie> query) {
        return new MovieDataServiceImpl(realm, query);
    }


}
