package com.example.fiddlest.popularmovie_v3;

import android.app.Application;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fiddlest on 2016-04-02.
 */
@Module
public class NetworkModule {

    private String baseUrl;

    public NetworkModule(String baseUrl){
        this.baseUrl = baseUrl;
    }

    @Provides @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
         return new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
         .client(okHttpClient).build();
    }

    @Provides @Singleton
    public OkHttpClient provideOkHttpClient(Cache cache){
        return new OkHttpClient
                .Builder()
                .cache(cache)
                .connectTimeout(45, TimeUnit.SECONDS)
                .build();
    }

    @Provides @Singleton
    public Cache provideOkHttpCache(Application app){
        return new Cache(app.getCacheDir(),10 * 1024 * 1024);
    }
}
