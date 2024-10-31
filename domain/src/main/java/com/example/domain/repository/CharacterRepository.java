package com.example.domain.repository;

import com.example.domain.model.Character;

import java.util.List;

public interface CharacterRepository {
    void getCharactersAsync(CharacterCallback callback);
    void getFavoriteCharactersAsync(CharacterCallback callback);
    List<Character> getAllFavoriteCharacters();  // Новый метод

    interface CharacterCallback {
        void onSuccess(List<Character> characters);
        void onFailure(Throwable t);
    }
}
