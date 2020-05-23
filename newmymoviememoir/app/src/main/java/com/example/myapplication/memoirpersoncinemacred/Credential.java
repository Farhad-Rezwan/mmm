package com.example.myapplication.memoirpersoncinemacred;

import java.util.Date;

public class Credential {
    private Integer credentialid;
    private String username;
    private String passwordhash;
    private Date signupdate;
    private Person personid;


    public Credential() {
    }

    public Credential(String username, String passwordhash, Person personid) {
        this.credentialid = credentialid;
        this.username = username;
        this.passwordhash = passwordhash;
    }

    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public Date getSignupdate() {
        return signupdate;
    }

    public void setSignupdate(Date signupdate) {
        this.signupdate = signupdate;
    }

    public Person getPersonid() {
        return personid;
    }

    public void setPersonid(Person personid) {
        this.personid = personid;
    }

}