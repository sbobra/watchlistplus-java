package com.example.watchlistplusj.ui.models;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.watchlistplusj.ui.api.TmdbApiController;
import com.example.watchlistplusj.ui.api.TmdbMovie;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Movie {
    public int id;
    public String title;
    public String imageUrl;
    public String release_date;
    public String original_title;
    public int year;

    public Movie(TmdbMovie movie) {
        this.id = movie.id;
        this.title = movie.title;
        this.imageUrl = getPosterPath(movie.poster_path);
        this.release_date = movie.release_date;
        Log.i("Movie", "release date: " + release_date);
        this.original_title = movie.original_title;
        this.year = getYear(movie.release_date);
    }

    public String getPosterPath(String path) {
        return TmdbApiController.BASE_URL_IMAGES + path;
    }

    public int getYear(String releaseDate) {
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            date = format.parse(releaseDate);
            if (date != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                return calendar.get(Calendar.YEAR);
            }
        } catch (ParseException e) {
            Log.i("Movie", Objects.requireNonNull(e.getMessage()));
        }
        return -1;

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
