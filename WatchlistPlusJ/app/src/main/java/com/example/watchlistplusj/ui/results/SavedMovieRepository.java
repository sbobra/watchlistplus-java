package com.example.watchlistplusj.ui.results;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.watchlistplusj.ui.sqlite.SavedMovie;
import com.example.watchlistplusj.ui.sqlite.SavedMovieDao;
import com.example.watchlistplusj.ui.sqlite.SavedMovieRoomDatabase;

import java.util.List;

class SavedMovieRepository {

    private SavedMovieDao savedMovieDao;
    LiveData<List<SavedMovie>> allMovies;


    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    SavedMovieRepository(Application application) {
        SavedMovieRoomDatabase db = SavedMovieRoomDatabase.getDatabase(application);
        savedMovieDao = db.savedMovieDao();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<SavedMovie>> getAllMovies() {
         allMovies = savedMovieDao.getAllMovies();
        return allMovies;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(List<SavedMovie> savedMovie) {
        SavedMovieRoomDatabase.databaseWriteExecutor.execute(() -> {
            savedMovieDao.insertAll(savedMovie);
        });
    }
}