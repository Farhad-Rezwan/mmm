package com.example.myapplication.memoirpersoncinemacred;

import android.text.Editable;

import java.io.Serializable;

public class Cinema implements Serializable {
    int cinemaid;
    String cinemaname;
    String cinemasuburb;
    Double latitude;
    Double longitude;

    public Cinema(String cinemaname, String cinemasuburb) {
        this.cinemaname = cinemaname;
        this.cinemasuburb = cinemasuburb;
    }

    public Cinema() {
    }

    public Cinema(String cinemaname, String cinemasuburb, Double latitude, Double longitude) {
        this.cinemaname = cinemaname;
        this.cinemasuburb = cinemasuburb;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCinemaname() {
        return cinemaname;
    }

    public void setCinemaname(String cinemaname) {
        this.cinemaname = cinemaname;
    }

    public String getCinemasuburb() {
        return cinemasuburb;
    }

    public void setCinemasuburb(String cinemasuburb) {
        this.cinemasuburb = cinemasuburb;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
