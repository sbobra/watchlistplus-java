package com.example.watchlistplusj.ui.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchlistplusj.databinding.ResultsItemViewBinding;
import com.example.watchlistplusj.ui.models.Movie;
import com.squareup.picasso.Picasso;

public class ResultsAdapter extends ListAdapter<Movie, ResultsAdapter.MovieViewHolder> {
    ResultsItemViewBinding binding;

    public ResultsAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate
        binding = ResultsItemViewBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    public static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Movie oldUser, @NonNull Movie newUser) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldUser.id == newUser.id;
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean areContentsTheSame(
                        @NonNull Movie oldUser, @NonNull Movie newUser) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldUser.equals(newUser);
                }
            };

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ResultsItemViewBinding binding;

        public MovieViewHolder(ResultsItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Movie movie) {
            binding.setMovie(movie);
                Picasso.get()
                        .load(movie.imageUrl)
                        .into(binding.poster);
            //.placeholder(R.drawable.placeholder)

        }
    }
}