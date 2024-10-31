package com.example.rick_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.AppDatabase;
import com.example.data.FavoriteCharacterDao;
import com.example.data.FavoriteCharacterRepositoryImpl;
import com.example.data.model.FavoriteCharacterEntity;
import com.example.domain.repository.FavoriteCharacterRepository;

import java.util.ArrayList;
import java.util.List;

public class FavoriteCharactersActivity extends AppCompatActivity {
    private RecyclerView favoriteCharacterRecyclerView;
    private CharacterAdapter characterAdapter;
    private FavoriteCharacterDao favoriteCharacterDao;
    private FavoriteCharacterRepository favoriteCharacterRepository;
    private CharacterViewModel characterViewModel;

    private ImageButton menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_characters);

        favoriteCharacterRecyclerView = findViewById(R.id.favoriteCharacterRecyclerView);
        favoriteCharacterRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoriteCharacterDao = AppDatabase.getInstance(this).favoriteCharacterDao();
        favoriteCharacterRepository = new FavoriteCharacterRepositoryImpl(this); // Передаем контекст

        // Инициализация ViewModel
        characterViewModel = new ViewModelProvider(this,
                new ViewModelProvider.Factory() {
                    @NonNull
                    @Override
                    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                        return (T) new CharacterViewModel(favoriteCharacterRepository);
                    }
                }).get(CharacterViewModel.class);

        menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(FavoriteCharactersActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        loadFavoriteCharacters();
    }

    private void loadFavoriteCharacters() {
        new Thread(() -> {
            List<FavoriteCharacterEntity> favoriteCharacters = favoriteCharacterDao.getAllFavoriteCharacters();
            runOnUiThread(() -> {
                List<com.example.domain.model.Character> characters = convertToCharacters(favoriteCharacters); // Используем правильный тип
                characterAdapter = new CharacterAdapter(characters, characterViewModel); // Передаем ViewModel
                favoriteCharacterRecyclerView.setAdapter(characterAdapter);
            });
        }).start();
    }

    private List<com.example.domain.model.Character> convertToCharacters(List<FavoriteCharacterEntity> favoriteCharacterEntities) {
        List<com.example.domain.model.Character> characters = new ArrayList<>();
        for (FavoriteCharacterEntity entity : favoriteCharacterEntities) {
            com.example.domain.model.Character character = new com.example.domain.model.Character(
                    entity.getId(),
                    entity.getName(),
                    entity.getSpecies(),
                    entity.getStatus(),
                    entity.getImageUrl(),
                    entity.getGender()
            );
            characters.add(character);
        }
        return characters;
    }
}
