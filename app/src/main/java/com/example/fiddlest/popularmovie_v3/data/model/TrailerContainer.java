package com.example.fiddlest.popularmovie_v3.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by fiddlest on 2016-03-14.
 */
public class TrailerContainer implements Parcelable {
    private ArrayList<Trailer> youtube;

    public TrailerContainer(){
        this.youtube = new ArrayList<>();
    }

    public ArrayList<Trailer> getTrailers() {
        return youtube;
    }


    public void setYoutube(ArrayList<Trailer> youtube) {
        this.youtube = youtube;
    }

    @Override
    public String toString() {
        return "TrailerContainer{" +
                "youtube=" + youtube +
                '}';
    }

    protected TrailerContainer(Parcel in) {
        if (in.readByte() == 0x01) {
            youtube = new ArrayList<Trailer>();
            in.readList(youtube, Trailer.class.getClassLoader());
        } else {
            youtube = null;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (youtube == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(youtube);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<TrailerContainer> CREATOR = new Creator<TrailerContainer>() {
        @Override
        public TrailerContainer createFromParcel(Parcel in) {
            return new TrailerContainer(in);
        }

        @Override
        public TrailerContainer[] newArray(int size) {
            return new TrailerContainer[size];
        }
    };
}