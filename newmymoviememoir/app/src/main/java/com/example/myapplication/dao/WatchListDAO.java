package com.example.myapplication.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entity.MovieEntity;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface WatchListDAO {
    @Query("SELECT * FROM MovieEntity")
    List<MovieEntity> getAll();

    @Query("SELECT * FROM MovieEntity WHERE uid = :customerId LIMIT 1")
    MovieEntity findByID(int customerId);

    @Insert
    void insertAll(MovieEntity... movieEntities);

    @Insert
    long insert(MovieEntity movieEntity);

    @Delete
    void delete(MovieEntity movieEntity);

    @Update(onConflict = REPLACE)
    void updateCustomers(MovieEntity... movieEntities);

    @Query("DELETE FROM MovieEntity")
    void deleteAll(); }

