package com.example.rick_app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button enterButton = findViewById(R.id.enter_button);
        Button recipeButton = findViewById(R.id.recipe_button);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        mainViewModel = new MainViewModel(sharedPreferences);

        // Наблюдение за состоянием авторизации
        mainViewModel.getIsLoggedIn().observe(this, isLoggedIn -> {
            recipeButton.setEnabled(isLoggedIn);
        });

        enterButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            startActivity(intent);
        });

        recipeButton.setOnClickListener(v -> {
            // Переход на экран персонажей, если пользователь авторизован
            if (mainViewModel.getIsLoggedIn().getValue() != null && mainViewModel.getIsLoggedIn().getValue()) {
             Intent intent = new Intent(MainActivity.this, CharactersActivity.class);
             startActivity(intent);
            } else {
                // Если пользователь не авторизован, перенаправляем его на экран входа
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                startActivity(intent);
            }
        });
    }
}
