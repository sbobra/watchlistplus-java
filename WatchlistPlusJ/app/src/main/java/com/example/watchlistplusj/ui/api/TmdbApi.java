package com.example.watchlistplusj.ui.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbApi {

    @GET("search/movie")
    Call<TmdbMovieResponse> searchMovie(@Query("api_key") String apiKey, @Query("query") String query);
}
