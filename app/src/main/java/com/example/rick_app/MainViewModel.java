package com.example.rick_app;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>();
    private final SharedPreferences sharedPreferences;

    public MainViewModel(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        checkLoginStatus();
    }

    private void checkLoginStatus() {
        boolean loggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        isLoggedIn.setValue(loggedIn);
    }

    public LiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }
}
