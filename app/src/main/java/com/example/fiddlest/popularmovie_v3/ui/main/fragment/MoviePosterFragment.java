package com.example.fiddlest.popularmovie_v3.ui.main.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.example.fiddlest.popularmovie_v3.App;
import com.example.fiddlest.popularmovie_v3.MovieModule;
import com.example.fiddlest.popularmovie_v3.R;
import com.example.fiddlest.popularmovie_v3.data.model.Movie;
import com.example.fiddlest.popularmovie_v3.presenter.MainView;
import com.example.fiddlest.popularmovie_v3.presenter.moviePoster.DaggerMoviePosterComponent;
import com.example.fiddlest.popularmovie_v3.presenter.moviePoster.MoviePosterComponent;
import com.example.fiddlest.popularmovie_v3.presenter.moviePoster.MoviePosterModule;
import com.example.fiddlest.popularmovie_v3.presenter.moviePoster.MoviePosterPresenter;
import com.example.fiddlest.popularmovie_v3.presenter.moviePoster.MoviePosterView;
import com.example.fiddlest.popularmovie_v3.ui.main.adapter.MoviePosterAdapter;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by fiddlest on 2016-04-03.
 */
public class MoviePosterFragment extends Fragment implements MoviePosterView {

    @Bind(R.id.movie_grid)
    GridView gridView;

    @Inject
    MoviePosterPresenter mMoviePosterPresenter;
    @Inject MovieModule.MovieApiService movieApiService;
    @Inject MoviePosterAdapter mMoviePosterAdapter;

    private boolean mIsDual = true;
    private MainView mMainView;
    private Movie mMovie;
    private String mPosterSortBy="";
    private String mPreviousSortBy="";
    private final String EXTRA_MOVIE="extra_movie";
    private final String EXTRA_SORT_ORDER= "sortOrder";
    private String TAG = "MoviePosterFragment";

    public static MoviePosterFragment newInstance(String posterSortBy) {
        MoviePosterFragment moviePosterFragment = new MoviePosterFragment();
        Bundle bundle = new Bundle();
        bundle.putString("sortOrder", posterSortBy);
        moviePosterFragment.setArguments(bundle);
        return moviePosterFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initMoviePosterComponent();
        if(savedInstanceState== null){
            this.mPosterSortBy = this.getArguments().getString("sortOrder");
        }else{
            this.mPosterSortBy = savedInstanceState.getString(EXTRA_SORT_ORDER);
        }
        //Init movie data load
        this.loadMovieData(this.mPosterSortBy, 1);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_MOVIE,this.mMovie);
        outState.putString(EXTRA_SORT_ORDER,mPosterSortBy);
        super.onSaveInstanceState(outState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_movie_poster, container, false);
        ButterKnife.bind(this, view);
        if(savedInstanceState!=null ){
            this.mMovie = savedInstanceState.getParcelable(EXTRA_MOVIE);
        }
        //Set empty adapter to grid view
        gridView.setAdapter(mMoviePosterAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsDual = getActivity().findViewById(R.id.dual_pane_layout) != null;
        mMainView = (MainView) getActivity();
    }

    public void initMoviePosterComponent() {
        MoviePosterComponent mMoviePosterComponent = DaggerMoviePosterComponent
                .builder()
                .appComponent(((App) getActivity().getApplication()).getComponent())
                .moviePosterModule(new MoviePosterModule(this)).build();
        mMoviePosterComponent.inject(this);
    }

    /**
     * This function update movie data and pass first movie from data list if device is tablet
     * @param movies
     * @param isFavorite
     */
    @Override
    public void movieLoadCompleted(ArrayList<Movie> movies, boolean isFavorite) {
        mMoviePosterAdapter.updateData(movies, isFavorite);
        //If configuration changes
        if(this.mMovie == null || !mPreviousSortBy.equals(mPosterSortBy)){
            this.mMovie = movies.get(0);
        }
        if(this.mIsDual ){
            mMainView.showMovieDetail(this.mMovie);
        }

    }

    /**
     * Load movie data from server or db
     * @param sortBy
     * @param page
     */
    public void loadMovieData(String sortBy, int page) {
        if(!sortBy.equals( this.mPreviousSortBy)){
            this.mPreviousSortBy = this.mPosterSortBy;
        }
        this.mPosterSortBy = sortBy;
        this.mMoviePosterPresenter.loadMovieData(sortBy, page);
    }
    /**
     * This function
     * @param position
     */
    @OnItemClick(R.id.movie_grid)
    public void onItemClick(int position){
        this.mMovie = (Movie) mMoviePosterAdapter.getItem(position);
        mMainView.showMovieDetail( this.mMovie);
    }
}
