package com.example.watchlistplusj.ui.sqlite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {SavedMovie.class}, version = 1, exportSchema = false)
public abstract class SavedMovieRoomDatabase extends RoomDatabase {

    public abstract SavedMovieDao wordDao();

    private static volatile SavedMovieRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static SavedMovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SavedMovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SavedMovieRoomDatabase.class, "saved_movie_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}