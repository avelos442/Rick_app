package com.example.rick_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.data.FavoriteCharacterRepositoryImpl;
import com.example.data.UserRepository;
import com.example.domain.repository.FavoriteCharacterRepository;
import com.example.domain.repository.UserRepositoryInterface;

public class ProfileActivity extends AppCompatActivity {

    private TextView emailTextView;
    private TextView usernameTextView;
    private TextView favoriteCountTextView; // TextView для отображения количества избранных персонажей
    private ImageButton menuButton;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d("ProfileActivity", "ProfileActivity Created");

        // Инициализация репозиториев
        UserRepositoryInterface userRepository = new UserRepository(this);
        FavoriteCharacterRepository favoriteCharacterRepository = new FavoriteCharacterRepositoryImpl(this);

        // Инициализация ViewModel
        profileViewModel = new ProfileViewModel(userRepository, favoriteCharacterRepository);

        // Отображаем email и логин на странице профиля
        emailTextView = findViewById(R.id.email_text);
        usernameTextView = findViewById(R.id.username_text);
        favoriteCountTextView = findViewById(R.id.favorite_characters_text);

        // Наблюдаем за изменениями данных пользователя
        profileViewModel.getUserEmail().observe(this, email -> {
            emailTextView.setText("Почта: " + email);
        });

        profileViewModel.getUsername().observe(this, username -> {
            usernameTextView.setText("Джерри из вселенной: " + username);
        });

        profileViewModel.getFavoriteCount().observe(this, count ->
                favoriteCountTextView.setText("Избранные персонажи: " + count));

        // Логика кнопки перехода в меню
        menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MenuActivity.class);
            startActivity(intent);
        });
    }

}
