package com.example.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.data.model.FavoriteCharacterEntity;

import java.util.List;

@Dao
public interface FavoriteCharacterDao {
    @Insert
    void insert(FavoriteCharacterEntity character);

    @Query("SELECT * FROM favorite_characters")
    List<FavoriteCharacterEntity> getAllFavoriteCharacters();

    @Query("DELETE FROM favorite_characters WHERE id = :characterId")
    void deleteFavoriteCharacter(int characterId);

    @Query("SELECT * FROM favorite_characters WHERE name = :name LIMIT 1")
    FavoriteCharacterEntity getFavoriteByName(String name);

    @Query("SELECT * FROM favorite_characters WHERE id = :id LIMIT 1")
    FavoriteCharacterEntity getFavoriteById(int id);
}

