package com.example.watchlistplusj.ui.home;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.watchlistplusj.ui.results.FiveStarView;

public class HomeViewModel extends ViewModel {

    public MutableLiveData<String> userInput = new MutableLiveData<>("");
    public MutableLiveData<Boolean> showListView = new MutableLiveData<>(false);
    public MutableLiveData<FiveStarView.StarRating> starRating = new MutableLiveData<>();

    public HomeViewModel() {
    }

    public void onHomeClicked() {
        showListView.setValue(true);
    }

    public void onStarClicked(FiveStarView.StarRating rating) {
        starRating.setValue(rating);
    }
}