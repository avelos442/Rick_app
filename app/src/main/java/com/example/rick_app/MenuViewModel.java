package com.example.rick_app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MenuViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isItHumanEnabled = new MutableLiveData<>(false);

    // LiveData для получения состояния отображения кнопки "Это человек?"
    public LiveData<Boolean> getIsItHumanEnabled() {
        return isItHumanEnabled;
    }

    // Метод для активации отображения кнопки "Это человек?"
    public void enableIsItHumanButton() {
        isItHumanEnabled.setValue(true);
    }
}
