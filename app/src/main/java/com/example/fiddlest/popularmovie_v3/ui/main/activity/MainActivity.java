package com.example.fiddlest.popularmovie_v3.ui.main.activity;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.example.fiddlest.popularmovie_v3.R;
import com.example.fiddlest.popularmovie_v3.data.model.Movie;
import com.example.fiddlest.popularmovie_v3.presenter.MainView;
import com.example.fiddlest.popularmovie_v3.ui.main.fragment.MovieDetailFragment;
import com.example.fiddlest.popularmovie_v3.ui.main.fragment.MoviePosterFragment;

public class MainActivity extends AppCompatActivity implements MainView {

    private final String TAG = "MainActivity";
    private boolean mIsDual;
    private final String EXTRA_MOVIE="extra_movie";
    private final String MOVIE_SORT = "extra_sort";
    private final String DETAIL_FRAGMENT_TAG = "detail_fragment";
    private final String POSTER_FRAGMENT_TAG = "poster_fragment";
    private MoviePosterFragment mMoviePosterFragment;
    private MovieDetailFragment mMovieDetailFragment;
    private boolean mIsNetworkUp = false;
    private String lastSortOrder ="";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.verifyStoragePermissions(this);

        //If internet is not connected show favorite movie automatically
        mIsDual = this.findViewById(R.id.dual_pane_layout) != null;
        //Internet connectivity and save
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork!= null &&  activeNetwork.isConnectedOrConnecting()){
            mIsNetworkUp = true;
        }

        if(savedInstanceState!=null){
            this.lastSortOrder =  savedInstanceState.getString(MOVIE_SORT);
            this.mMoviePosterFragment = (MoviePosterFragment) getFragmentManager().findFragmentByTag(POSTER_FRAGMENT_TAG);
        }else{
            //set default sort order depends on network status
            this.setDefaultSortOrder(mIsNetworkUp);
            //Init poster fragment
        }
        this.setMoviePosterFragment( this.lastSortOrder);
        //start fragment
        this.startMoviePosterFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //If internet is down, hide two items
        if(!mIsNetworkUp){
            menu.findItem(R.id.action_popularity).setVisible(false);
            menu.findItem(R.id.action_vote).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if(id ==R.id.action_popularity){
            this.lastSortOrder = "popularity.desc";
        }else if(id == R.id.action_vote){
            this.lastSortOrder = "vote_average.desc";
        }else if(id== R.id.action_favorite){
            this.lastSortOrder = "favorite";
        }
        mMoviePosterFragment.loadMovieData(  this.lastSortOrder,1);
        return super.onOptionsItemSelected(item);
    }
    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Save current movie sort order
        outState.putString(MOVIE_SORT, this.lastSortOrder );

        super.onSaveInstanceState(outState);
    }

    public void setMoviePosterFragment(String sortBy){
        if(mMoviePosterFragment==null)
             mMoviePosterFragment = MoviePosterFragment.newInstance(sortBy);
    }
    public void startMoviePosterFragment(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.movie_grid_holder, mMoviePosterFragment,POSTER_FRAGMENT_TAG)
                .commit();
    }


    /**
     * This function calls different function depends on view size
     * @param movie
     */
    @Override
    public void showMovieDetail(Movie movie) {
        if(this.mIsDual){
            this.loadDetailFragment(movie);
        }else{
            this.launchDetailActivity(movie);
        }
    }

    /**
     * Launch fragment if current device tablet
     * @param movie
     */
    private void loadDetailFragment(Movie movie){
        mMovieDetailFragment = MovieDetailFragment.newInstance(movie);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.movie_detail_holder, mMovieDetailFragment,DETAIL_FRAGMENT_TAG)
                .commit();
    }

    /**
     * Launch activity if current device is  phone
     * @param movie
     */
    private void launchDetailActivity(Movie movie){
        Intent detailIntent = new Intent(this,DetailActivity.class);
        detailIntent.putExtra(this.EXTRA_MOVIE,movie);
        startActivity(detailIntent);
    }

    private void setDefaultSortOrder(boolean isNetworkUp){
        if(isNetworkUp){
            this.lastSortOrder = "popularity.desc";
        }else{
            this.lastSortOrder = "favorite";
        }
    }
}
