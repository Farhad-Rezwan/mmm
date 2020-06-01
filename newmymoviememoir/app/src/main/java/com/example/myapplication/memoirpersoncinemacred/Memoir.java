package com.example.myapplication.memoirpersoncinemacred;

import java.math.BigDecimal;
import java.util.Date;

public class Memoir {
    private Integer memoirid;
    private String moviename;
    private Date] moviereleasedate;
    private Date datetimewatched;
    private String comment;
    private BigDecimal rating;
    private Cinema cinemaid;
    private Person personid;

    public Memoir() {
    }

    public Memoir(Integer memoirid) {
        this.memoirid = memoirid;
    }

    public Memoir(Integer memoirid, String moviename, Date moviereleasedate, Date datetimewatched) {
        this.memoirid = memoirid;
        this.moviename = moviename;
        this.moviereleasedate = moviereleasedate;
        this.datetimewatched = datetimewatched;
    }

    public Integer getMemoirid() {
        return memoirid;
    }

    public void setMemoirid(Integer memoirid) {
        this.memoirid = memoirid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public Date getMoviereleasedate() {
        return moviereleasedate;
    }

    public void setMoviereleasedate(Date moviereleasedate) {
        this.moviereleasedate = moviereleasedate;
    }

    public Date getDatetimewatched() {
        return datetimewatched;
    }

    public void setDatetimewatched(Date datetimewatched) {
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

    public Cinema getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(Cinema cinemaid) {
        this.cinemaid = cinemaid;
    }

    public Person getPersonid() {
        return personid;
    }

    public void setPersonid(Person personid) {
        this.personid = personid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memoirid != null ? memoirid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Memoir)) {
            return false;
        }
        Memoir other = (Memoir) object;
        if ((this.memoirid == null && other.memoirid != null) || (this.memoirid != null && !this.memoirid.equals(other.memoirid))) {
            return false;
        }
        return true;
    }
}
