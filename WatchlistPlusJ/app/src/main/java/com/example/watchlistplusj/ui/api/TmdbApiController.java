package com.example.watchlistplusj.ui.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TmdbApiController {
    static final String API_KEY = "026a795344dd54a25466dc332d3da881";
    static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String BASE_URL_IMAGES = "https://image.tmdb.org/t/p/w500/";
    Retrofit retrofit;
    TmdbApi tmdbApi;


    public TmdbApiController() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        tmdbApi = retrofit.create(TmdbApi.class);
    }

    public void searchMovies(String query, Callback<TmdbMovieResponse> callback) {
        Call<TmdbMovieResponse> call = tmdbApi.searchMovie(API_KEY, query);
        call.enqueue(callback);
    }
}
