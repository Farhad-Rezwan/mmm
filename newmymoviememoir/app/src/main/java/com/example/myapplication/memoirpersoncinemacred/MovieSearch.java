package com.example.myapplication.memoirpersoncinemacred;


import java.io.Serializable;

public class MovieSearch implements Serializable {
    private String movieName;
    private String releaseYear;
    private String movieURL;
    private String infor;
    private String rate;


    public MovieSearch(String movieName, String releaseYear, String movieURL, String infor, String rate) {
        this.movieName = movieName;
        this.releaseYear = releaseYear;
        this.movieURL = movieURL;
        this.infor = infor;
        this.rate = rate;
    }

    public MovieSearch(String movieName, String releaseYear, String rate) {
        this.movieName = movieName;
        this.releaseYear = releaseYear;
        this.rate = rate;
    }


    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getMovieURL() {
        return movieURL;
    }

    public void setMovieURL(String movieURL) {
        this.movieURL = movieURL;
    }


    public String getInfor() {
        return infor;
    }

    public void setInfor(String infor) {
        this.infor = infor;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
