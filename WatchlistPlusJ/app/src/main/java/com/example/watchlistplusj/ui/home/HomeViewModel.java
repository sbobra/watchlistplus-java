package com.example.watchlistplusj.ui.home;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    public MutableLiveData<String> userInput = new MutableLiveData<>("");
    public MutableLiveData<Boolean> showListView = new MutableLiveData<>(false);

    public HomeViewModel() { }

    public void onHomeClicked() {
        showListView.setValue(true);
    }
}