package com.example.fiddlest.popularmovie_v3.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by fiddlest on 2016-03-13.
 */
public class ReviewsWithMeta implements Parcelable {
    private int page;
    private ArrayList<Review> results;
    private int total_pages;
    private int total_results;

    public ReviewsWithMeta(){

        this.page = 0;
        this.results = new ArrayList<>();
        this.total_pages = 0;
        this.total_results =0;
    }


    @Override
    public String toString() {
        return "ReviewsWithMeta{" +
                "page=" + page +
                ", results=" + results +
                ", total_pages=" + total_pages +
                ", total_results=" + total_results +
                '}';
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<Review> getResults() {
        return results;
    }

    public void setResults(ArrayList<Review> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    protected ReviewsWithMeta(Parcel in) {
        page = in.readInt();
        if (in.readByte() == 0x01) {
            results = new ArrayList<Review>();
            in.readList(results, Review.class.getClassLoader());
        } else {
            results = null;
        }
        total_pages = in.readInt();
        total_results = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        if (results == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(results);
        }
        dest.writeInt(total_pages);
        dest.writeInt(total_results);
    }

    @SuppressWarnings("unused")
    public static final Creator<ReviewsWithMeta> CREATOR = new Creator<ReviewsWithMeta>() {
        @Override
        public ReviewsWithMeta createFromParcel(Parcel in) {
            return new ReviewsWithMeta(in);
        }

        @Override
        public ReviewsWithMeta[] newArray(int size) {
            return new ReviewsWithMeta[size];
        }
    };
}