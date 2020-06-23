package com.example.myapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.dao.WatchListDAO;
import com.example.myapplication.entity.MovieEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MovieEntity.class}, version = 2, exportSchema = false) public abstract class WatchListDatabase extends RoomDatabase {
    public abstract WatchListDAO customerDao();
    private static WatchListDatabase INSTANCE;
    //we create an ExecutorService with a fixed thread pool so we can later run database operations asynchronously on a background thread.
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static synchronized WatchListDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    WatchListDatabase.class, "CustomerDatabase") .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
