package com.example.rick_app;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domain.model.Character;
import com.example.domain.repository.FavoriteCharacterRepository;

import java.util.concurrent.Executors;

public class CharacterViewModel extends ViewModel {
    private final FavoriteCharacterRepository favoriteCharacterRepository;
    private static final String TAG = "CharacterViewModel";

    public CharacterViewModel(FavoriteCharacterRepository favoriteCharacterRepository) {
        this.favoriteCharacterRepository = favoriteCharacterRepository;
    }

    public LiveData<Boolean> isFavorite(Character character) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            boolean isFavorite = favoriteCharacterRepository.isFavorite(character.getId());
            result.postValue(isFavorite);
            Log.d(TAG, "Checking if character is favorite: " + character.getName() + " - " + isFavorite);
        });
        return result;
    }

    public void deleteFavoriteCharacter(int characterId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            favoriteCharacterRepository.deleteFavoriteCharacter(characterId);
            Log.d(TAG, "Character deleted from favorites: ID " + characterId);
        });
    }

    public void addFavoriteCharacter(Character character) {
        Executors.newSingleThreadExecutor().execute(() -> {
            favoriteCharacterRepository.addFavoriteCharacter(character);
            Log.d(TAG, "Character added to favorites: " + character.getName());
        });
    }
}
