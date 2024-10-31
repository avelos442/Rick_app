package com.example.rick_app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.data.FirebaseAuthRepository;
import com.example.domain.usecases.LoginUser;
import com.example.domain.usecases.RegisterUser;
import com.example.domain.repository.AuthRepository;

public class AuthViewModel extends ViewModel {
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final LoginUser loginUser;

    public AuthViewModel(AuthRepository authRepository) {
        this.loginUser = new LoginUser(authRepository);
    }

    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void login(String email, String password) {
        loginUser.execute(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {
                loginSuccess.postValue(true);
            }

            @Override
            public void onFailure(String error) {
                errorMessage.postValue(error);
            }
        });
    }
}

