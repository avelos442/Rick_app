package com.example.domain.repository;

import com.example.domain.model.Character;

import java.util.List;

public interface FavoriteCharacterRepository {
    void addFavoriteCharacter(Character character);
    void deleteFavoriteCharacter(int characterId);
    boolean isFavorite(int characterId);
    void getFavoriteCharactersAsync(CharacterCallback callback); // Асинхронный метод для получения любимых персонажей
    List<Character> getAllFavoriteCharacters(); // Синхронный метод для получения всех любимых персонажей

    interface CharacterCallback {
        void onSuccess(List<Character> characters);
        void onFailure(Throwable t);
    }
}
