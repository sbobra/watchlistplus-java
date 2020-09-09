package com.example.watchlistplusj.ui.results;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.watchlistplusj.R;
import com.example.watchlistplusj.databinding.FragmentResultsBinding;
import com.example.watchlistplusj.ui.adapters.ResultsAdapter;
import com.example.watchlistplusj.ui.api.TmdbMovie;
import com.example.watchlistplusj.ui.models.Movie;

import java.util.ArrayList;

public class ResultsFragment extends Fragment {

    private ResultsViewModel resultsViewModel;
    private FragmentResultsBinding binding;
    private GridLayoutManager gridLayoutManager;
    private ResultsAdapter resultsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        initializeBindingAndViewModel(inflater, container);

        assert getArguments() != null;
        String searchTerm = ResultsFragmentArgs.fromBundle(getArguments()).getSearchTerm();
        Log.i("ResultsFragment", "Search term " + searchTerm);
        resultsViewModel.setSearchTerm(searchTerm);

        initializeRecyclerView();
        updateText();
        updateAdapter();

        return binding.getRoot();
    }

    private void initializeBindingAndViewModel(@NonNull LayoutInflater inflater,
                                               ViewGroup container) {
        // TODO: activity lifecycle instead of fragment lifecycle
        // private val watchListViewModel: WatchlistViewModel by activityViewModels()
        resultsViewModel =
                ViewModelProviders.of(requireActivity()).get(ResultsViewModel.class);
        binding = FragmentResultsBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setResultsViewModel(resultsViewModel);
    }

    private void initializeRecyclerView() {
        resultsAdapter = new ResultsAdapter();
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        binding.resultsRecyclerView.setLayoutManager(gridLayoutManager);
        binding.resultsRecyclerView.setAdapter(resultsAdapter);
    }

    private void updateAdapter() {
        resultsViewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            Log.i("ResultsFragment", "onChanged");
            for (Movie movie : movies) {
                Log.i("Resultsfragment", "submitting list: " + movie.title);
            }
            resultsAdapter.submitList(movies);
        });
    }

    // update via livedata, can remove and update via databinding
    private void updateText() {
        resultsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.textNotifications.setText(s);
            }
        });
    }

    // update via livedata, can remove and update via databinding
    private void printMovies() {
        resultsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.textNotifications.setText(s);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}