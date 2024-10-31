package com.example.rick_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private MenuViewModel menuViewModel;
    private Button allCharactersButton;
    private Button favouriteButton;
    private Button isItHumanButton;
    private Button myAccountButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Инициализация ViewModel
        menuViewModel = new MenuViewModel();

        // Инициализация кнопок
        allCharactersButton = findViewById(R.id.all_characters_button);
        favouriteButton = findViewById(R.id.favourite_button);
        isItHumanButton = findViewById(R.id.is_it_human_button);
        myAccountButton = findViewById(R.id.my_account_button);
        backButton = findViewById(R.id.back_button);

        // Логика кнопки "Все персонажи"
        allCharactersButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, CharactersActivity.class); // Переход на экран со всеми персонажами
            startActivity(intent);
        });

        // Логика кнопки "Избранное"
        favouriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, FavoriteCharactersActivity.class); // Переход на экран избранных персонажей
            startActivity(intent);
        });

        // Логика кнопки "Это человек?"
        isItHumanButton.setOnClickListener(v -> {
//            Intent intent = new Intent(MenuActivity.this, IsHumanCheckActivity.class); // Переход на экран проверки на человека
//            startActivity(intent);
        });

        // Логика кнопки "Мой профиль"
        myAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ProfileActivity.class); // Переход на экран профиля
            startActivity(intent);
        });

        // Логика кнопки "Назад"
        backButton.setOnClickListener(v -> {
            finish(); // Завершает текущую активность и возвращает на предыдущую
        });
    }
}
