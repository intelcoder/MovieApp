package com.example.fiddlest.popularmovie_v3;

import android.app.Application;
import com.example.fiddlest.popularmovie_v3.data.DataModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by fiddlest on 2016-04-02.
 */
public class App extends Application{

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.setRealmConfiguration();
        this.setupAppComponent();
    }

    public AppComponent getComponent() {
        return mAppComponent;
    }

    private void setupAppComponent(){
        String baseUrl = this.getResources().getString(R.string.movie_data_base_url);
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(baseUrl))
                .dataModule(new DataModule())
                .movieModule(new MovieModule())
                .build();
        mAppComponent.inject(this);
    }

    /**
     * This function set configuration for realm db
     */
    private void setRealmConfiguration(){
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder(this)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .name("movie.realm")
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }


}
