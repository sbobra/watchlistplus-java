package com.example.watchlistplusj.ui.sqlite;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedMovieDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SavedMovie word);

    @Query("DELETE FROM saved_movie_table")
    void deleteAll();

    @Query("SELECT * from saved_movie_table ORDER BY title ASC")
    LiveData<List<SavedMovie>> getAllMovies();
}