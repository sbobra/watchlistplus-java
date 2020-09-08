package com.example.watchlistplusj.ui.results;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.watchlistplusj.ui.api.TmdbApiController;
import com.example.watchlistplusj.ui.api.TmdbMovie;
import com.example.watchlistplusj.ui.api.TmdbMovieResponse;
import com.example.watchlistplusj.ui.models.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<Movie>> movies = new MutableLiveData<>();

    public ResultsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is results fragment");
        createMovieList();
        searchMovies();
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

    public void searchMovies() {
        TmdbApiController controller = new TmdbApiController();
        controller.searchMovies("test", new Callback<TmdbMovieResponse>() {
            @Override
            public void onResponse(Call<TmdbMovieResponse> call, Response<TmdbMovieResponse> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    List<TmdbMovie> movieList = response.body().results;
                    // TODO: figure out why we can't use movies.getValue() here
                    ArrayList<Movie> existingMovies = new ArrayList<>();
                    for (TmdbMovie movie : movieList) {
                        Log.i("ResultsViewModel", movie.title);
                        existingMovies.add(new Movie(movie.id, movie.title));
                    }
//                    existingMovies.addAll(movies.getValue());
                    movies.getValue().addAll(existingMovies);
//                    movies.setValue(existingMovies);
                } else {
                    assert response.errorBody() != null;
                    try {
                        Log.i("ResultsViewModel", "error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TmdbMovieResponse> call, Throwable t) {
                Log.i("ResultsViewModel", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}