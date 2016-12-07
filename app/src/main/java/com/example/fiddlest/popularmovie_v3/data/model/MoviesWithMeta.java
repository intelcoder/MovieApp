package com.example.fiddlest.popularmovie_v3.data.model;


import java.util.ArrayList;

/**
 * Created by fiddlest on 2016-04-04.
 */
public class MoviesWithMeta {
    private int page;
    private int total_results;
    private int total_pages;
    private ArrayList<Movie> results;

    @Override
    public String toString() {
        return "MovieDataContainer{" +
                "page=" + page +
                ", total_results=" + total_results +
                ", total_pages=" + total_pages +
                ", results=" + results +
                '}';
    }

    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }
    public Movie getMovieByIndex(int index){
        return results.get(index);
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }
}
