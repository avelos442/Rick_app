package com.example.rick_app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domain.model.Character; // Импортируйте вашу модель Character
import com.example.domain.repository.FavoriteCharacterRepository; // Импортируйте нужный репозиторий
import com.example.domain.repository.UserRepositoryInterface;
import com.example.domain.usecases.UserService;

import java.util.List;

public class ProfileViewModel extends ViewModel {
    private UserRepositoryInterface userRepository;
    private FavoriteCharacterRepository favoriteCharacterRepository; // Изменено на FavoriteCharacterRepository

    private final MutableLiveData<String> userEmail = new MutableLiveData<>();
    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<Integer> favoriteCount = new MutableLiveData<>();

    public ProfileViewModel(UserRepositoryInterface userRepository, FavoriteCharacterRepository favoriteCharacterRepository) { // Изменено
        this.userRepository = userRepository;
        this.favoriteCharacterRepository = favoriteCharacterRepository;

        loadUserData();
        loadFavoriteCount();
    }

    private void loadUserData() {
        userEmail.setValue(userRepository.getEmail());
        String userUsername = userRepository.getUsername();
        if (userUsername == null) {
            UserService userService = new UserService();
            userUsername = userService.generateRandomUsername();
            userRepository.saveUsername(userUsername);
        }
        username.setValue(userUsername);
    }

    private void loadFavoriteCount() {
        favoriteCharacterRepository.getFavoriteCharactersAsync(new FavoriteCharacterRepository.CharacterCallback() {
            @Override
            public void onSuccess(List<Character> characters) {
                favoriteCount.postValue(characters.size());
            }

            @Override
            public void onFailure(Throwable t) {
                favoriteCount.postValue(0);
            }
        });
    }

    public LiveData<String> getUserEmail() {
        return userEmail;
    }

    public LiveData<String> getUsername() {
        return username;
    }

    public LiveData<Integer> getFavoriteCount() {
        return favoriteCount;
    }
}
