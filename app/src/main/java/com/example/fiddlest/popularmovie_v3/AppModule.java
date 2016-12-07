package com.example.fiddlest.popularmovie_v3;

import android.app.Application;
import android.content.Context;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by fiddlest on 2016-04-02.
 */
@Module
public class AppModule {
    private App app;

    public AppModule(App app){
        this.app = app;
    }

    @Provides @Singleton
    public Application provideApplication(){
        return app;
    }

    @Provides @Singleton
    public Context provideContext(){
        return app.getApplicationContext();
    }



}
