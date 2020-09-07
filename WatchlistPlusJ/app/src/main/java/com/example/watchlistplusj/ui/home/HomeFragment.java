package com.example.watchlistplusj.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.watchlistplusj.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setHomeViewModel(homeViewModel);
        View root = binding.getRoot();

        homeViewModel.showListView.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                View v = getView();
                if (v != null && b) {
                    navigateToResults(v, homeViewModel.userInput.getValue());
                }
            }
        });
        return root;
    }

    private void navigateToResults(View v, String searchTerm) {
        HomeFragmentDirections.ActionNavigationHomeToResultsPage action =
                HomeFragmentDirections
                        .actionNavigationHomeToResultsPage();
        if (searchTerm != null) {
            action.setSearchTerm(searchTerm);
        }
        Navigation.findNavController(v).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}