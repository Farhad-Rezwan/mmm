package com.example.myapplication.save;

import android.app.Application;

import com.example.myapplication.memoirpersoncinemacred.Person;

public class PersonObject extends Application {
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
