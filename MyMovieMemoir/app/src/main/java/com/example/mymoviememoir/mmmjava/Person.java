package com.example.mymoviememoir.mmmjava;


import java.util.Date;

public class Person {
    private Integer personid;
    private String firstname;
    private String surname;
    private Character gender;
    private Date dob;
    private String address;
    private String stateau;
    private int postcode;

    public Person(String firstname, String surname, Character gender, Date dob, String address, String stateau, int postcode) {
        this.firstname = firstname;
        this.surname = surname;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.stateau = stateau;
        this.postcode = postcode;
    }


    public Integer getPersonid() {
        return personid;
    }

    public void setPersonid(Integer personid) {
        this.personid = personid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStateau() {
        return stateau;
    }

    public void setStateau(String stateau) {
        this.stateau = stateau;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }


}

