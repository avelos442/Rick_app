package com.example.rick_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.CharacterRepositoryImpl;
import com.example.data.FavoriteCharacterDao;
import com.example.data.FavoriteCharacterRepositoryImpl;
import com.example.data.AppDatabase;
import com.example.domain.model.Character;
import com.example.data.model.FavoriteCharacterEntity;
import com.example.domain.repository.CharacterRepository;
import com.example.domain.repository.FavoriteCharacterRepository;

import java.util.ArrayList;
import java.util.List;

public class CharactersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CharacterAdapter characterAdapter;
    private CharacterRepository characterRepository;
    private FavoriteCharacterRepository favoriteCharacterRepository;
    private CharacterViewModel characterViewModel;
    private ImageButton menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);

        // Инициализация репозитория любимых персонажей
        favoriteCharacterRepository = new FavoriteCharacterRepositoryImpl(this); // Передаем контекст
        characterRepository = new CharacterRepositoryImpl(this, favoriteCharacterRepository); // Передаем контекст и репозиторий

        // Инициализация ViewModel
        characterViewModel = new ViewModelProvider(this, new CharacterViewModelFactory(favoriteCharacterRepository))
                .get(CharacterViewModel.class);

        // Инициализация RecyclerView и Adapter
        recyclerView = findViewById(R.id.characterRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        characterAdapter = new CharacterAdapter(new ArrayList<>(), characterViewModel);
        recyclerView.setAdapter(characterAdapter);

        // Загрузка персонажей через ViewModel
        loadCharacters();

        // Логика кнопки перехода в меню
        menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(CharactersActivity.this, MenuActivity.class);
            startActivity(intent);
        });
    }

    private List<Character> convertToCharacters(List<FavoriteCharacterEntity> favoriteCharacterEntities) {
        List<Character> characters = new ArrayList<>();
        for (FavoriteCharacterEntity entity : favoriteCharacterEntities) {
            Character character = new Character(
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

    private void loadCharacters() {
        characterRepository.getCharactersAsync(new CharacterRepository.CharacterCallback() {
            @Override
            public void onSuccess(List<Character> characters) {
                if (characters != null) {
                    List<FavoriteCharacterEntity> favoriteCharacterEntities = convertToFavoriteCharacterEntities(characters);
                    List<Character> characterList = convertToCharacters(favoriteCharacterEntities);
                    characterAdapter.updateData(characterList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("CharactersActivity", "Failed to load characters: " + t.getMessage());
                Toast.makeText(CharactersActivity.this, "Error loading characters", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<FavoriteCharacterEntity> convertToFavoriteCharacterEntities(List<Character> characters) {
        List<FavoriteCharacterEntity> favoriteCharacters = new ArrayList<>();
        for (Character character : characters) {
            favoriteCharacters.add(new FavoriteCharacterEntity(
                    character.getId(),
                    character.getName(),
                    character.getStatus(),
                    character.getSpecies(),
                    character.getImage(),
                    character.getGender()
            ));
        }
        return favoriteCharacters;
    }
}
