package com.example.fiddlest.popularmovie_v3;

import com.example.fiddlest.popularmovie_v3.data.model.Movie;
import com.example.fiddlest.popularmovie_v3.data.model.MoviesWithMeta;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by fiddlest on 2016-04-03.
 */
@Module
public class MovieModule {
    public interface MovieApiService {
        @GET("3/discover/movie")
        Observable<MoviesWithMeta> getMovieList(@Query("sort_by") String sort, @Query("api_key") String api_key, @Query("page") int page);
        @GET("3/movie/{id}?append_to_response=trailers,reviews")
        //Observable<Movie> getMovieDetail(@Path("id") int id,@Query("api_key") String api_key);
        Observable<Movie> getMovieDetail(@Path("id") int id, @Query("api_key") String api_key);
    }
    @Provides
    public MovieApiService provideMovieApiService(Retrofit retrofit){
        return retrofit.create(MovieApiService.class);
    }


}
