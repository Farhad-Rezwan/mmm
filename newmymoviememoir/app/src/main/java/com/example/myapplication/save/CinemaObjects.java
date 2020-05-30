package com.example.myapplication.save;

import android.app.Application;

import com.example.myapplication.memoirpersoncinemacred.Cinema;

public class CinemaObjects extends Application {
    private Cinema cinema;


    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
