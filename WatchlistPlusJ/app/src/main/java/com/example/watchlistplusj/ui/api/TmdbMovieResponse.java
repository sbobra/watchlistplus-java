package com.example.watchlistplusj.ui.api;

import java.util.List;

public class TmdbMovieResponse {
    public int page;
    public int total_results;
    public int total_pages;
    public List<TmdbMovie> results;
}
