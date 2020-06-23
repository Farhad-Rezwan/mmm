package com.example.myapplication.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myapplication.dao.WatchListDAO;
import com.example.myapplication.database.WatchListDatabase;
import com.example.myapplication.entity.MovieEntity;

import java.util.List;

public class CustomerRepository {
    private WatchListDAO dao;
    private LiveData<List<MovieEntity>> allCustomers; private MovieEntity movieEntity;
    public CustomerRepository(Application application){
        WatchListDatabase db = WatchListDatabase.getInstance(application); dao=db.customerDao();
    }
    public LiveData<List<MovieEntity>> getAllCustmers() {

        allCustomers = (LiveData<List<MovieEntity>>) dao.getAll();

        return allCustomers;
    }
    public void insert(final MovieEntity movieEntity){
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
        @Override
        public void run() { dao.insert(movieEntity);
        } });
    }
    public void deleteAll(){
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
        @Override
        public void run() {
            dao.deleteAll(); }
    }); }
    public void delete(final MovieEntity movieEntity){
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
        @Override
        public void run() {
            dao.delete(movieEntity); }
    });
    }
    public void insertAll(final MovieEntity... movieEntities){
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() { dao.insertAll(movieEntities); }
        }); }

    public void updateCustomers(final MovieEntity... movieEntities){ WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
        @Override
        public void run() { dao.updateCustomers(movieEntities); }
    });
    }
    public MovieEntity findByID(final int customerId){
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() { @Override
        public void run() {
            MovieEntity runMovieEntity = dao.findByID(customerId); setMovieEntity(runMovieEntity);
        } });
        return movieEntity;
    }
    public void setMovieEntity(MovieEntity movieEntity){
        this.movieEntity = movieEntity;
    }
}
