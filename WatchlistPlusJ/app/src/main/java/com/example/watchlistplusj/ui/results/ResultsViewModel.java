package com.example.watchlistplusj.ui.results;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.watchlistplusj.ui.api.TmdbApiController;
import com.example.watchlistplusj.ui.api.TmdbMovie;
import com.example.watchlistplusj.ui.api.TmdbMovieResponse;
import com.example.watchlistplusj.ui.models.Movie;
import com.example.watchlistplusj.ui.sqlite.SavedMovie;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<Movie>> movies = new MutableLiveData<>();
    private String searchTerm;
    private SavedMovieRepository savedMovieRepository;

    public ResultsViewModel(Application application) {
        super(application);
        savedMovieRepository = new SavedMovieRepository(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is results fragment");
        createMovieList();
//        searchMovies();
    }

    public void createMovieList() {
        ArrayList<Movie> movieList = new ArrayList<>();
        movies.setValue(movieList);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return movies;
    }

    public void searchMovies() {
        if (searchTerm == null) {
            return;
        }
        TmdbApiController controller = new TmdbApiController();
        controller.searchMovies(searchTerm, new Callback<TmdbMovieResponse>() {
            @Override
            public void onResponse(Call<TmdbMovieResponse> call, Response<TmdbMovieResponse> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    List<TmdbMovie> movieList = response.body().results;
                    ArrayList<Movie> existingMovies = new ArrayList<>();
                    for (TmdbMovie movie : movieList) {
                        Log.i("ResultsViewModel", movie.title);
                        existingMovies.add(new Movie(movie.id, movie.title));
                    }
                    existingMovies.addAll(0, movies.getValue());
                    // This needs to be a DIFFERENT object reference to the list
                    movies.setValue(existingMovies);

                    //TODO: uncomment this
//                    persistMovies(existingMovies);
//                    printMovies();
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

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
        searchMovies();
    }

    public void persistMovies(List<Movie> movies) {
        List<SavedMovie> savedMovies = new ArrayList<>();
        for (Movie m : movies) {
            savedMovies.add(new SavedMovie(m.id, m.title));
        }
        savedMovieRepository.insert(savedMovies);
    }

    public void printMovies() {
        LiveData<List<SavedMovie>> movies = savedMovieRepository.getAllMovies();
        // TODO: observe livedata
    }
}