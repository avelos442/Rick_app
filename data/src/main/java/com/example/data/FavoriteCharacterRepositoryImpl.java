package com.example.data;

import android.content.Context;

import com.example.data.model.FavoriteCharacterEntity;
import com.example.domain.model.Character;
import com.example.domain.repository.FavoriteCharacterRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class FavoriteCharacterRepositoryImpl implements FavoriteCharacterRepository {
    private final FavoriteCharacterDao favoriteCharacterDao;
    private final ExecutorService executorService;

    public FavoriteCharacterRepositoryImpl(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        favoriteCharacterDao = db.favoriteCharacterDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void addFavoriteCharacter(Character character) {
        FavoriteCharacterEntity entity = new FavoriteCharacterEntity(
                character.getId(),
                character.getName(),
                character.getStatus(),
                character.getSpecies(),
                character.getGender(),
                character.getImage()
        );
        executorService.execute(() -> favoriteCharacterDao.insert(entity)); // Выполняем вставку в фоне
    }

    @Override
    public List<Character> getAllFavoriteCharacters() {
        List<FavoriteCharacterEntity> entities = favoriteCharacterDao.getAllFavoriteCharacters();
        return entities.stream().map(entity -> new Character(
                entity.getId(),
                entity.getName(),
                entity.getSpecies(),
                entity.getStatus(),
                entity.getGender(), // Добавляем gender
                entity.getImageUrl()
        )).collect(Collectors.toList());
    }

    @Override
    public void deleteFavoriteCharacter(int characterId) {
        favoriteCharacterDao.deleteFavoriteCharacter(characterId);
    }

    @Override
    public boolean isFavorite(int characterId) {
        return favoriteCharacterDao.getFavoriteById(characterId) != null;
    }

    @Override
    public void getFavoriteCharactersAsync(CharacterCallback callback) {
        executorService.execute(() -> {
            List<FavoriteCharacterEntity> entities = favoriteCharacterDao.getAllFavoriteCharacters();
            if (entities != null && !entities.isEmpty()) {
                List<Character> favoriteCharacters = entities.stream().map(entity -> new Character(
                        entity.getId(),
                        entity.getName(),
                        entity.getSpecies(),
                        entity.getStatus(),
                        entity.getGender(),
                        entity.getImageUrl()
                )).collect(Collectors.toList());
                callback.onSuccess(favoriteCharacters);
            } else {
                callback.onFailure(new Exception("No favorite characters found"));
            }
        });
    }
}
