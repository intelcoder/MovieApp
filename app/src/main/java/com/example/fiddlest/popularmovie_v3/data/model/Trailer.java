package com.example.fiddlest.popularmovie_v3.data.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fiddlest on 2016-03-14.
 */
public class Trailer implements Parcelable {
    private String name;
    private String size;
    private String source;
    private String type;

    @Override
    public String toString() {
        return "Trailer{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getSource() {
        return source;
    }

    public String getName(){return name;}
    protected Trailer(Parcel in) {
        name = in.readString();
        size = in.readString();
        source = in.readString();
        type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(size);
        dest.writeString(source);
        dest.writeString(type);
    }

    @SuppressWarnings("unused")
    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
}