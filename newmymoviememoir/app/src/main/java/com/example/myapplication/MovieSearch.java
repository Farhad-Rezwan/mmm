package com.example.myapplication;


public class MovieSearch {

    public MovieSearch(String movieName, String releaseYear, String movieURL) {
        this.movieName = movieName;
        this.releaseYear = releaseYear;
        this.movieURL = movieURL;
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

    private String movieName;
    private String releaseYear;
    private String movieURL;

}
