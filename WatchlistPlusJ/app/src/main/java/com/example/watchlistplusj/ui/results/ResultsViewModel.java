package com.example.watchlistplusj.ui.results;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.watchlistplusj.ui.models.Movie;

import java.util.ArrayList;

public class ResultsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<Movie>> movies = new MutableLiveData<>();

    public ResultsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is results fragment");
        createMovieList();
    }

    public void createMovieList() {
        ArrayList<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            movieList.add(new Movie(i, "Movie " + i));
        }
        movies.setValue(movieList);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return movies;
    }
}