package com.example.fiddlest.popularmovie_v3.ui.main.activity;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.example.fiddlest.popularmovie_v3.R;
import com.example.fiddlest.popularmovie_v3.data.model.Movie;
import com.example.fiddlest.popularmovie_v3.ui.main.fragment.MovieDetailFragment;


public class DetailActivity extends AppCompatActivity {
    private final String EXTRA_MOVIE="extra_movie";
    private Movie mMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        this.mMovie = getIntent().getParcelableExtra(this.EXTRA_MOVIE);
        this.loadDetailFragment(mMovie);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mMovie = outState.getParcelable(EXTRA_MOVIE);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        savedInstanceState.getParcelable(EXTRA_MOVIE);
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * This function launch detail fragment
     * @param movie
     */
    private void loadDetailFragment(Movie movie){
        MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(movie);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.movie_detail_holder, movieDetailFragment )
                .commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
