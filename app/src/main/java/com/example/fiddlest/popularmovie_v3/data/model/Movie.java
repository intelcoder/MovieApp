package com.example.fiddlest.popularmovie_v3.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by fiddlest on 2016-02-02.
 */
public class Movie implements Parcelable {
    private int id;
    private String poster_path;
    private String overview;
    private String release_date;
    private String original_title;
    private int    runtime;
    private float  vote_average;
    private ReviewsWithMeta reviews;
    private TrailerContainer trailers;
    private boolean favorite;

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", poster_path='" + poster_path + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", original_title='" + original_title + '\'' +
                ", runtime=" + runtime +
                ", vote_average=" + vote_average +
                ", reviews=" + reviews +
                ", trailerContainer=" + trailers +
                '}';
    }

    public Movie(int id, String overview, String release_date, String original_title, float vote_average, String poster_path,int runtime) {
        this.id = id;
        this.overview = overview;
        this.release_date = release_date;
        this.original_title = original_title;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.runtime = runtime;
        this.reviews = new ReviewsWithMeta();
        this.trailers = new TrailerContainer();
    }
    public Movie(RealmMovie realmMovie){
        this.id = realmMovie.getId();
        this.overview = realmMovie.getOverview();
        this.release_date = realmMovie.getRelease_date();
        this.original_title = realmMovie.getOriginal_title();
        this.vote_average = realmMovie.getVote_average();
        this.poster_path = realmMovie.getPoster_path();
        this.runtime = realmMovie.getRuntime();
        this.reviews = new ReviewsWithMeta();
        this.trailers = new TrailerContainer();
        this.favorite = true;
    }


    public int getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }


    public int getRuntime() {
        return runtime;
    }

    public float getVote_average() {
        return vote_average;
    }


  public ArrayList<Review> getReviews() {
        return reviews.getResults();
    }

    public TrailerContainer getTrailerContainer() {
        return trailers;
    }


    public boolean isFavorite() {
        return favorite;
    }

    public void setTrailers(ArrayList<Trailer> trailers){
        this.trailers.setYoutube(trailers);

    }
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setPoster_path(String poster_path){
        this.poster_path = poster_path;
    }
    protected Movie(Parcel in) {
        id = in.readInt();
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        original_title = in.readString();
        runtime = in.readInt();
        vote_average = in.readFloat();
        reviews = (ReviewsWithMeta) in.readValue(ReviewsWithMeta.class.getClassLoader());
        trailers = (TrailerContainer) in.readValue(Trailer.class.getClassLoader());
        favorite = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(original_title);
        dest.writeInt(runtime);
        dest.writeFloat(vote_average);
        dest.writeValue(reviews);
        dest.writeValue(trailers);
        dest.writeByte((byte) (favorite ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}