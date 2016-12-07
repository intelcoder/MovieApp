package com.example.fiddlest.popularmovie_v3.ui.main.fragment;


import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.fiddlest.popularmovie_v3.App;
import com.example.fiddlest.popularmovie_v3.R;
import com.example.fiddlest.popularmovie_v3.data.model.Movie;
import com.example.fiddlest.popularmovie_v3.presenter.movieDetail.MovieDetailComponent;
import com.example.fiddlest.popularmovie_v3.presenter.movieDetail.MovieDetailModule;
import com.example.fiddlest.popularmovie_v3.presenter.movieDetail.MovieDetailPresenter;
import com.example.fiddlest.popularmovie_v3.presenter.movieDetail.MovieDetailView;
import com.example.fiddlest.popularmovie_v3.ui.main.adapter.ReviewRecyclerViewAdapter;
import com.example.fiddlest.popularmovie_v3.ui.main.adapter.TrailerRecyclerViewAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.File;
import javax.inject.Inject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fiddlest on 2016-04-03.
 */
public class MovieDetailFragment extends Fragment implements MovieDetailView {
    @Bind(R.id.image_view_star) ImageView star;
    @Bind(R.id.detail_top) RelativeLayout detailTop;
    @Bind(R.id.scrollView) ScrollView scrollView;
    @Bind(R.id.moviePoster) ImageView image_movie;
    @Bind(R.id.text_view_movie_title) TextView text_title;
    @Bind(R.id.text_view_releaseDate) TextView text_releaseDate;
    @Bind(R.id.text_view_movieRating) TextView text_rating;
    @Bind(R.id.text_view_runtime) TextView text_runtime;
    @Bind(R.id.text_view_movieOverView) TextView text_overView;
    @Bind(R.id.reviewRecycler) RecyclerView reviewRecycler;
    @Bind(R.id.trailerRecycler) RecyclerView trailerRecyclerView;
    @Bind(R.id.text_view_trailer_title) TextView trailerTitle;
    @Bind(R.id.ReviewTitle) TextView reviewTitle;
    @Bind(R.id.detail_progressbar) ProgressBar detailProgressbar;

    @Inject public MovieDetailPresenter mMovieDetailPresenter;
    @Inject public Context mContext;
    private final String TAG = "MovieDetailFragment";
    private static final String MOVIE_KEY = "detail_movie";
    private boolean isNetworkUp=false;

    private TrailerRecyclerViewAdapter mTrailerRecyclerViewAdapter;


    public static MovieDetailFragment newInstance(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_KEY, movie);
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        movieDetailFragment.setArguments(bundle);
        return movieDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initMovieDetailComponent();
        Log.d("PPP","onActivityCreated");
        if(savedInstanceState==null){
            Movie movie = this.getArguments().getParcelable(MOVIE_KEY);
            //Check network state
            ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                this.isNetworkUp=true;
            }
            this.mMovieDetailPresenter.setMovie(movie);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(MOVIE_KEY,mMovieDetailPresenter.getMovie());
        super.onSaveInstanceState(outState);
    }


    public void initMovieDetailComponent() {
        MovieDetailComponent mMoviePosterComponent = com.example.fiddlest.popularmovie_v3.presenter.movieDetail.DaggerMovieDetailComponent
                .builder()
                .appComponent(((App) getActivity().getApplication()).getComponent())
                .movieDetailModule(new MovieDetailModule(this))
                .build();
        mMoviePosterComponent.inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("PPP","onActivityCreated");
        if(savedInstanceState==null) {
            mMovieDetailPresenter.getMovieDetail(this.isNetworkUp);
        }else{
            Movie movie = savedInstanceState.getParcelable(MOVIE_KEY);
            mMovieDetailPresenter.setMovie(movie);
            this.showMovieDetail(movie);
        }
    }

    @Override
    public void fillStar() {
        star.setImageResource(R.drawable.ic_star_rate_black_18px);
    }

    /**
     * When this function triggered it saves movie into db
     * @param v
     */
    @OnClick(R.id.image_view_star)
    public void onClick(View v) {
        String posterPath = mMovieDetailPresenter.getMovie().getPoster_path();

        File targetFile = this.getPosterSaveLocation(posterPath);
        Target picassoTarget = mMovieDetailPresenter.getPicassoTarget(targetFile);
        Observable
                .just(picassoTarget)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        target -> Picasso
                                .with(mContext)
                                .load(mContext.getResources().getString(R.string.movie_data_poster_base_url) + posterPath)
                                .into(target),
                        e -> {
                        },
                        this::onPosterSaveCompleted);
    }

    /**
     *  This function return poster saving path depends on storage state
     * @param fileName poster.jpg filename
     * @return File
     */
    private File getPosterSaveLocation(String fileName) {
        File target;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            target = new File(android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName);
            Log.d("TTT", android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + fileName);
        } else {
            target = new File(mContext.getCacheDir(), fileName);
        }
        return target;

    }

    /**
     * this function executed when poster of movie successfully saved
     */
    public void onPosterSaveCompleted(){
        mMovieDetailPresenter.saveFavoriteMovie();
        this.fillStar();
        Toast.makeText(getActivity(),"Movie Successfully Saved",Toast.LENGTH_LONG).show();
    }

    /**
     * This function shows movie detail on the screen
     * @param movie
     */
    @Override
    public void showMovieDetail(Movie movie) {
        this.setRecyclerViewAdapter(movie);
        this.loadPoster(movie);

        String runtime = String.valueOf(movie.getRuntime()) + " mins";
        String movieVoteAvg = String.valueOf(movie.getVote_average()) +"/10";
        //If it is 10 parse value to int in order to delete .0
        if(movie.getVote_average()>=10){
            movieVoteAvg = String.valueOf( ((int) movie.getVote_average()));
        }

        text_title.setText(movie.getOriginal_title());
        text_releaseDate.setText(movie.getRelease_date());
        text_rating.setText(movieVoteAvg);
        text_runtime.setText(runtime);
        text_overView.setText(movie.getOverview());

        //without these two function view focused on last element of recycler view
        scrollView.isSmoothScrollingEnabled();
        scrollView.smoothScrollTo(0,0);

        this.showView();
    }


    /**
     * This function hide progressbar and show view
     */
    public void showView() {
        detailProgressbar.setVisibility(View.GONE);
        detailTop.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
    }

    /**
     * This function set Adapter for recycler view
     * @param movie
     */
    private void setRecyclerViewAdapter(Movie movie){
        //get count
        int trailerSize = movie.getTrailerContainer().getTrailers().size();
        int reviewSize = movie.getReviews().size();
        if (trailerSize > 0) {
            TrailerRecyclerViewAdapter mTrailerRecyclerViewAdapter = new TrailerRecyclerViewAdapter(movie.getTrailerContainer().getTrailers());
            trailerRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            trailerRecyclerView.setAdapter(mTrailerRecyclerViewAdapter);
            trailerTitle.setVisibility(View.VISIBLE);
        }


        if (reviewSize > 0) {
            ReviewRecyclerViewAdapter mReviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(movie.getReviews());
            reviewRecycler.setLayoutManager(new LinearLayoutManager(mContext));
            reviewRecycler.setAdapter(mReviewRecyclerViewAdapter);

            reviewTitle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if(mTrailerRecyclerViewAdapter!= null && mTrailerRecyclerViewAdapter.getItemCount()>0)
            mTrailerRecyclerViewAdapter.releaseLoader();
        super.onDestroy();


    }

    /**
     * This function load poster from either  db or server
     * @param movie
     */
    private void loadPoster(Movie movie){
        if(movie.isFavorite()){
            File file = new File(movie.getPoster_path());
            Picasso
                    .with(mContext.getApplicationContext())
                    .load(file)
                    .placeholder(R.drawable.movie_icon)
                    .error(R.drawable.movie_icon)
                    .into(image_movie);
        }else{
            Picasso
                    .with(mContext)
                    .load(mContext.getResources().getString(R.string.movie_data_poster_base_url) + movie.getPoster_path())
                    .placeholder(R.drawable.movie_icon)
                    .error(R.drawable.movie_icon)
                    .into(image_movie);
        }
    }


}
