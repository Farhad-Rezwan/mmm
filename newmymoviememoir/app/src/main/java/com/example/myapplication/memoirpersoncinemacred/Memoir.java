package com.example.myapplication.memoirpersoncinemacred;

import java.math.BigDecimal;
import java.util.Date;

public class Memoir {
    private Integer memoirid;
    private String moviename;
    private String moviereleasedate;
    private String datetimewatched;
    private String comment;
    private BigDecimal rating;
    private int cinemaid;
    private int personid;

    public Memoir() {
    }

    public Memoir(Integer memoirid) {
        this.memoirid = memoirid;
    }

    public Memoir(String moviename, String moviereleasedate, String datetimewatched, String comment, BigDecimal rating, int cinemaid, int personid) {
        this.moviename = moviename;
        this.moviereleasedate = moviereleasedate;
        this.datetimewatched = datetimewatched;
        this.comment = comment;
        this.rating = rating;
        this.cinemaid = cinemaid;
        this.personid = personid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getMoviereleasedate() {
        return moviereleasedate;
    }

    public void setMoviereleasedate(String moviereleasedate) {
        this.moviereleasedate = moviereleasedate;
    }

    public String getDatetimewatched() {
        return datetimewatched;
    }

    public void setDatetimewatched(String datetimewatched) {
        this.datetimewatched = datetimewatched;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public int getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(int cinemaid) {
        this.cinemaid = cinemaid;
    }

    public int getPersonid() {
        return personid;
    }

    public void setPersonid(int personid) {
        this.personid = personid;
    }
}
