package com.example.watchlistplusj.ui.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Movie {
    public int id;
    public String title;
    public String imageUrl;

    public Movie(int id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                Objects.equals(title, movie.title);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
