package com.example.watchlistplusj.ui.sqlite;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {SavedMovie.class}, version = 1, exportSchema = false)
public abstract class SavedMovieRoomDatabase extends RoomDatabase {

    public abstract SavedMovieDao savedMovieDao();

    private static volatile SavedMovieRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static SavedMovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SavedMovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SavedMovieRoomDatabase.class, "saved_movie_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
//                SavedMovieDao dao = INSTANCE.savedMovieDao();
//                dao.deleteAll();
//
//                SavedMovie word = new SavedMovie(5000, "testMovie1");
//                dao.insert(word);
//                word = new SavedMovie(6000, "testMovie2");
//                dao.insert(word);
            });
        }
    };
}