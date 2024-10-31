package com.example.rick_app;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.domain.repository.FavoriteCharacterRepository;

public class CharacterViewModelFactory implements ViewModelProvider.Factory {
    private final FavoriteCharacterRepository favoriteCharacterRepository;

    public CharacterViewModelFactory(FavoriteCharacterRepository favoriteCharacterRepository) {
        this.favoriteCharacterRepository = favoriteCharacterRepository;
    }

    @Override
    @NonNull
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CharacterViewModel.class)) {
            return (T) new CharacterViewModel(favoriteCharacterRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}

